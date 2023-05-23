package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.bluetooth.OBDCommunication
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.MobileDeviceInfo
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.common.trip.VehicleInfo
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.trip.SaveTripSummaryUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.SaveOBDReadingUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.SendOBDReadingUseCase
import hr.fer.carpulse.util.PhoneUtils
import hr.fer.carpulse.util.getAppVersion
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import java.util.*

// TODO: check if the bluetoothController should be used as api and accessed trough a separate repository class
// TODO: refactor the phone utils - containing context
class HomeScreenViewModel(
    private val bluetoothController: BluetoothController,
    private val phoneUtils: PhoneUtils,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val sendTripStartInfoUseCase: SendTripStartInfoUseCase,
    private val readLocalStorageStateUseCase: ReadLocalStorageStateUseCase,
    private val saveOBDReadingUseCase: SaveOBDReadingUseCase,
    private val saveTripSummaryUseCase: SaveTripSummaryUseCase,
    private val sendOBDReadingUseCase: SendOBDReadingUseCase
) : ViewModel() {

    private val isMeasuring = MutableStateFlow(false)

    private val errorMessage = MutableStateFlow<String?>(null)

    private var readOBDDataJob: Job? = null

    private val obdReadingData = MutableSharedFlow<OBDReading>()

    var tripUUID: UUID? = null
    var tripStartTimestamp: Long = 0L

    private var storeDataLocally: Boolean = false

    // TODO: compare and store values according to obd readings
    var maxTripRPM: Int = 0
    var maxTripSpeed: Int = 0

    fun startMeasuring() {
        // TODO remove the lines below- added only for testing purposes
//        generateAndSendTripStartInfo()
//        // Read the value from DataStore
//        viewModelScope.launch(Dispatchers.IO) {
//            storeDataLocally = readLocalStorageStateUseCase().first()
//        }
        //TODO remove until here

        if (bluetoothController.isConnected.value) {
            isMeasuring.update { true }

            // Read the value from DataStore
            viewModelScope.launch(Dispatchers.IO) {
                storeDataLocally = readLocalStorageStateUseCase().first()
            }

            generateAndSendTripStartInfo()
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

        // save summary only if the trip was previously started
        if (tripStartTimestamp != 0L && tripUUID != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val tripSummary = TripSummary(
                    tripUUID = tripUUID.toString(),
                    startTimestamp = tripStartTimestamp,
                    endTimestamp = System.currentTimeMillis(),
                    maxRPM = maxTripRPM,
                    maxSpeed = maxTripSpeed,
                    sent = !storeDataLocally
                )

                saveTripSummaryUseCase(tripSummary)
            }
        }

        isMeasuring.update { false }
    }


    private fun readOBDData(): Job? {
        val socket = bluetoothController.getCurrentClientSocket().value

        return if (socket != null) {
            val obd = OBDCommunication(socket)
            // TODO: launch the coroutine with viewModelScope.launch(Dispatchers.IO) {}
            CoroutineScope(Dispatchers.IO).launch {
                while (true) {

                    val reading = obd.readData()

                    checkAndUpdateMaxValues(reading)
                    sendOrStoreReading(reading)

                    obdReadingData.emit(reading)

                    delay(5000L)
                }
            }
        } else {
            null
        }
    }

    private fun generateAndSendTripStartInfo() {
        // TODO: check if it is better to generate UUID with nameUUIDFromBytes and pass email and timestamp as parameter
        tripUUID = UUID.randomUUID()
        tripStartTimestamp = System.currentTimeMillis()

        viewModelScope.launch(Dispatchers.IO) {
            val driverData = getDriverDataUseCase().first()

            var pids0120: String = OBDReading.NO_DATA
            var pids2140: String = OBDReading.NO_DATA
            var pids4160: String = OBDReading.NO_DATA

            val socket = bluetoothController.getCurrentClientSocket().value
            if (socket != null) {
                val obd = OBDCommunication(socket)
                pids0120 = obd.getAvailablePids_01_20()
                pids2140 = obd.getAvailablePids_21_40()
                pids4160 = obd.getAvailablePids_41_60()
            }

            val vehicleInfo = VehicleInfo(
                fuelType = driverData.fuelType,
                vehicle = driverData.vehicleType,
                pids01_20 = pids0120,
                pids21_40 = pids2140,
                pids41_60 = pids4160
            )

            val mobileDeviceInfo = MobileDeviceInfo(
                appVersion = getAppVersion(),
                deviceName = phoneUtils.getDeviceName(),
                operator = phoneUtils.getPhoneOperator(),
                fingerprint = phoneUtils.getFingerprint(),
                androidId = phoneUtils.getAndroidId()
            )

            val tripStartInfo = TripStartInfo(
                vehicleInfo = vehicleInfo,
                mobileDeviceInfo = mobileDeviceInfo,
                tripId = tripUUID.toString(),
                tripStartTimestamp = tripStartTimestamp.toString()
            )

            Log.d("debug_log", "Sending trip start info...")
            sendTripStartInfoUseCase(tripStartInfo)
        }

    }

    /**
     * Checks whether the local storage is turned on, and sends the reading to server via API, or stores the reading locally,
     * depending on the value stored in DataStore.
     */
    private fun sendOrStoreReading(obdReading: OBDReading) {

        viewModelScope.launch(Dispatchers.IO) {
            if (storeDataLocally) {
                // store to DB
                saveOBDReadingUseCase(obdReading = obdReading, tripUUID = tripUUID.toString())
            } else {
                // send data to server
                sendOBDReadingUseCase(obdReading = obdReading, tripUUID = tripUUID.toString())
            }
        }
    }

    private fun checkAndUpdateMaxValues(obdReading: OBDReading) {
        var speed = obdReading.speed
        var rpm = obdReading.rpm

        if (rpm != OBDReading.NO_DATA) {
            rpm = rpm.removeSuffix("RPM")
            try {
                val rpmValue = rpm.toInt()
                if (rpmValue > maxTripRPM) {
                    maxTripRPM = rpmValue
                }

            } catch (e: Exception) {
                Log.d("debug_log", "Caught exception while parsing RPM: $e")
            }
        }

        if (speed != OBDReading.NO_DATA) {
            speed = speed.removeSuffix("km/h")
            try {
                val speedValue = speed.toInt()
                if (speedValue > maxTripSpeed) {
                    maxTripSpeed = speedValue
                }

            } catch (e: Exception) {
                Log.d("debug_log", "Caught exception while parsing speed: $e")
            }
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