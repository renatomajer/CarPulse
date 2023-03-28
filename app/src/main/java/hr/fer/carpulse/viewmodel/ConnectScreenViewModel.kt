package hr.fer.carpulse.viewmodel

import androidx.lifecycle.ViewModel
import hr.fer.carpulse.domain.common.BluetoothController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConnectScreenViewModel(
    private val bluetoothController: BluetoothController
) : ViewModel() {

    private val isScanning = MutableStateFlow(false)

    fun getScannedDevices() = bluetoothController.scannedDevices

    fun getPairedDevices() = bluetoothController.pairedDevices

    fun startScan() {
        bluetoothController.startDiscovery()
        isScanning.update { true }
    }

    fun stopScan() {
        bluetoothController.stopDiscovery()
        isScanning.update { false }
    }

    fun isScanning() = isScanning.asStateFlow()
}