package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.domain.common.BluetoothDevice
import hr.fer.carpulse.domain.common.ConnectionResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class ConnectDeviceViewModel(
    private val bluetoothController: BluetoothController
) : ViewModel() {

    private val isScanning = MutableStateFlow(false)

    private val connectingDeviceAddress = MutableStateFlow<String?>(null)
    private val isConnected = MutableStateFlow(false)

    private val errorMessage = MutableStateFlow<String?>(null)

    private var deviceConnectionJob: Job? = null

    init {
        bluetoothController.isConnected.onEach { connected ->
            isConnected.update { connected }
        }.launchIn(viewModelScope)

        bluetoothController.errors.onEach { error ->
            errorMessage.update { error }
        }.launchIn(viewModelScope)
    }

    fun getScannedDevices() = bluetoothController.scannedDevices

    fun getPairedDevices() = bluetoothController.pairedDevices

    fun connectToDevice(device: BluetoothDevice) {
        isScanning.update { false }
        connectingDeviceAddress.update { device.address }
        deviceConnectionJob = bluetoothController.connectToDevice(device).listen()
    }

    fun disconnectFromDevice() {
        deviceConnectionJob?.cancel()
        bluetoothController.closeConnection()
        connectingDeviceAddress.update { null }
        isConnected.update { false }
    }

    fun startScan() {
        bluetoothController.startDiscovery()
        isScanning.update { true }
    }

    fun stopScan() {
        bluetoothController.stopDiscovery()
        isScanning.update { false }
    }

    fun isScanning() = isScanning.asStateFlow()

    fun getConnectingDeviceAddress() = connectingDeviceAddress.asStateFlow()
    fun isConnected() = isConnected.asStateFlow()

    fun errorMessage() = errorMessage.asStateFlow()

    fun getConnectedDeviceAddress() = bluetoothController.getConnectedDeviceAddress()

    private fun Flow<ConnectionResult>.listen(): Job {
        return onEach { result ->
            when (result) {
                ConnectionResult.ConnectionEstablished -> {
                    isConnected.update { true }
                    connectingDeviceAddress.update { null }
                    errorMessage.update { null }
                }
                is ConnectionResult.Error -> {
                    isConnected.update { false }
                    connectingDeviceAddress.update { null }
                    errorMessage.update { result.message }
                }
            }
        }.catch { throwable ->
            bluetoothController.closeConnection()
            isConnected.update { false }
            connectingDeviceAddress.update { null }

        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
//        bluetoothController.release()
        bluetoothController.stopDiscovery()
        Log.d("debug_log", "ConnectScreenViewModel::onCleared()")
    }
}