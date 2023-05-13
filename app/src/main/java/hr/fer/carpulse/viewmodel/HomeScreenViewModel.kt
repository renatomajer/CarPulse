package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.bluetooth.OBDCommunication
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.domain.common.obd.OBDReading
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class HomeScreenViewModel(
    private val bluetoothController: BluetoothController
) : ViewModel() {

    private val isMeasuring = MutableStateFlow(false)

    private val errorMessage = MutableStateFlow<String?>(null)

    private var readOBDDataJob: Job? = null

    private val obdReadingData = MutableSharedFlow<OBDReading>()

    fun startMeasuring() {

        if (bluetoothController.isConnected.value) {
            isMeasuring.update { true }

            readOBDDataJob = readOBDData()

        } else {
            errorMessage.update { "Device not connected!" }
            // reset error message after 1 sec, so it can show the same message again
            viewModelScope.launch {
                delay(1000L)
                errorMessage.update { null }
            }
        }

    }

    fun stopMeasuring() {
        readOBDDataJob?.cancel()
        readOBDDataJob = null

        isMeasuring.update { false }
    }


    private fun readOBDData(): Job? {
        val socket = bluetoothController.getCurrentClientSocket().value

        return if (socket != null) {
            val obd = OBDCommunication(socket)
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {

                    val reading = obd.readData()
                    obdReadingData.emit(reading)

                    delay(5000L)
                }
            }
        } else {
            null
        }
    }

    fun getIsMeasuring() = isMeasuring.asStateFlow()

    fun getErrorMessages() = errorMessage.asStateFlow()

    fun getIsDeviceConnected() = bluetoothController.isConnected

    fun getOBDReadingDataFLow() = obdReadingData.asSharedFlow()

    override fun onCleared() {
        readOBDDataJob?.cancel()
        readOBDDataJob = null
        bluetoothController.release()
        Log.d("debug_log", "HomeScreenViewModel::onCleared")
        super.onCleared()
    }
}