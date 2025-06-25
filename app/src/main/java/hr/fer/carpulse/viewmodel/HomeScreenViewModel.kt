package hr.fer.carpulse.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.bluetooth.OBDCommunication
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.MobileDeviceInfo
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.common.trip.VehicleInfo
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.preferences.SaveLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.trip.SaveTripSummaryUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SendTripReadingDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.StopLocationDataUpdateUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.UpdateLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.SaveOBDReadingUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.SaveTripStartInfoUseCase
import hr.fer.carpulse.util.PhoneUtils
import hr.fer.carpulse.util.getAppVersion
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID


// TODO: check if the bluetoothController should be used as api and accessed trough a separate repository class
// TODO: refactor the phone utils - containing context
class HomeScreenViewModel(
    private val bluetoothController: BluetoothController,
    private val phoneUtils: PhoneUtils,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val sendTripStartInfoUseCase: SendTripStartInfoUseCase,
    private val saveTripStartInfoUseCase: SaveTripStartInfoUseCase,
    private val readLocalStorageStateUseCase: ReadLocalStorageStateUseCase,
    private val saveLocalStorageStateUseCase: SaveLocalStorageStateUseCase,
    private val saveOBDReadingUseCase: SaveOBDReadingUseCase,
    private val saveTripSummaryUseCase: SaveTripSummaryUseCase,
    private val getLocationDataUseCase: GetLocationDataUseCase,
    private val updateLocationDataUseCase: UpdateLocationDataUseCase,
    private val stopLocationDataUpdateUseCase: StopLocationDataUpdateUseCase,
    private val getWeatherDataUseCase: GetWeatherDataUseCase,
    private val getTrafficDataUseCase: GetTrafficDataUseCase,
    private val saveLocationDataUseCase: SaveLocationDataUseCase,
    private val saveWeatherDataUseCase: SaveWeatherDataUseCase,
    private val saveTrafficDataUseCase: SaveTrafficDataUseCase,
    private val connectToBrokerUseCase: ConnectToBrokerUseCase,
    private val disconnectFromBrokerUseCase: DisconnectFromBrokerUseCase,
    private val sendTripReadingDataUseCase: SendTripReadingDataUseCase,
    private val sendDriverDataUseCase: SendDriverDataUseCase,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val isMeasuring = MutableStateFlow(false)

    private val errorMessage = MutableStateFlow<String?>(null)

    var driverName by mutableStateOf<String?>(null)
        private set

    var vehicleType by mutableStateOf<String?>(null)
        private set

    var carImageIndex by mutableStateOf<Int?>(null)
        private set

    var avatarImageIndex by mutableStateOf<Int?>(null)
        private set

    private var readOBDDataJob: Job? = null
    private var sendOrStoreDataJob: Job? = null
    private var collectTrafficDataJob: Job? = null

    private val obdReadingData = MutableStateFlow<OBDReading>(OBDReading())

    private lateinit var locationDataFlow: StateFlow<LocationData>
    private var weatherData: WeatherData = WeatherData()
    private var trafficData: TrafficData? = null

    var tripUUID: UUID? = null
    var tripStartTimestamp: Long = 0L

    var storeDataLocally by mutableStateOf(false)
        private set

    var maxTripRPM: Int = 0
    var maxTripSpeed: Int = 0

    init {
        viewModelScope.launch {
            readLocalStorageStateUseCase().collect { storeLocally ->
                storeDataLocally = storeLocally
            }
        }
    }

    fun startMeasuring() {
        // TODO remove the lines below- added only for testing purposes
        // Read the value from DataStore
//        viewModelScope.launch(Dispatchers.IO) {
//            storeDataLocally = readLocalStorageStateUseCase().first()
//
//            if (!storeDataLocally) {
//                connectToBrokerUseCase()
//                getAndSendDriverData()
//            }
//            updateLocationDataUseCase()
//            locationDataFlow = getLocationDataUseCase()
//            delay(1000L)
//            generateAndSendTripStartInfo()
//            delay(2000L)
//            sendOrStoreDataJob = sendOrStoreData“()
//            getApiWeatherData()
//        }

        //TODO remove until here

        if (bluetoothController.isConnected.value) {
            isMeasuring.update { true }

            // Read the value from DataStore
            viewModelScope.launch(Dispatchers.IO) {
//                storeDataLocally = readLocalStorageStateUseCase().first()

                if (!storeDataLocally) {
                    connectToBrokerUseCase()
                    getAndSendDriverData() // send driver data so the web page can display correct information
                }

                updateLocationDataUseCase()
                locationDataFlow = getLocationDataUseCase()

                delay(1000L)
                generateAndSendTripStartInfo()

                delay(2000L)
                readOBDDataJob = readOBDData()
                collectTrafficDataJob = collectTrafficData()
                sendOrStoreDataJob = sendOrStoreData()
                getApiWeatherData()
            }

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
        stopLocationDataUpdateUseCase()

        readOBDDataJob?.cancel()
        readOBDDataJob = null

        sendOrStoreDataJob?.cancel()
        sendOrStoreDataJob = null

        collectTrafficDataJob?.cancel()
        collectTrafficDataJob = null


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

//        if (!storeDataLocally) {
//            disconnectFromBrokerUseCase()
//        }

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

                    obdReadingData.update { reading }

                    delay(2000L)
                }
            }
        } else {
            null
        }
    }

    private fun sendOrStoreData(): Job {
        return CoroutineScope(Dispatchers.IO).launch {
            while (true) {
                val reading = obdReadingData.value

                sendOrStoreTripReadingData(reading)

                delay(500L)
            }
        }
    }

    // could be called inside the readOBDData coroutine
    private fun generateAndSendTripStartInfo() {
        tripUUID = UUID.randomUUID()
        tripStartTimestamp = System.currentTimeMillis()

        viewModelScope.launch(Dispatchers.IO) {
            val driverData = getDriverDataUseCase().first()

            var pids0120: String = OBDReading.NA
            var pids2140: String = OBDReading.NA
            var pids4160: String = OBDReading.NA

            val socket = bluetoothController.getCurrentClientSocket().value
            if (socket != null) {
                val obd = OBDCommunication(socket)
                pids0120 = obd.getAvailablePids0120()
                pids2140 = obd.getAvailablePids2140()
                pids4160 = obd.getAvailablePids4160()
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

            if (storeDataLocally) {
                Log.d("debug_log", "Saving trip start info...")
                saveTripStartInfoUseCase(tripStartInfo)
            } else {
                Log.d("debug_log", "Sending trip start info...")
                sendTripStartInfoUseCase(tripStartInfo)
            }
        }

    }

    /**
     * Checks whether the local storage is turned on, and sends the data to server via API, or stores the data locally,
     * depending on the value stored in DataStore.
     */
    private fun sendOrStoreTripReadingData(obdReading: OBDReading) {

        viewModelScope.launch(Dispatchers.IO) {

            val latestTimestamp = locationDataFlow.value.timestamp

            if (storeDataLocally) {
                // store to DB

                Log.d("debug_log", "Saving OBD reading and location...")
                saveOBDReadingUseCase.invoke(
                    obdReading = obdReading.copy(timestamp = latestTimestamp),
                    tripUUID = tripUUID.toString()
                )
                saveLocationDataUseCase.invoke(
                    locationData = locationDataFlow.value,
                    tripUUID = tripUUID.toString()
                )

                trafficData?.let {
                    saveTrafficDataUseCase.invoke(
                        trafficData = it.copy(timestamp = latestTimestamp),
                        tripUUID = tripUUID.toString()
                    )
                }

            } else {
                // send data to server

                Log.d("debug_log", "Sending data...")

                sendTripReadingDataUseCase(
                    locationDataFlow.value,
                    weatherData,
                    tripUUID.toString(),
                    obdReading.copy(timestamp = latestTimestamp),
                    trafficData?.copy(timestamp = latestTimestamp)
                )
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

    private fun getApiWeatherData() {

        viewModelScope.launch(Dispatchers.IO) {

            getWeatherDataUseCase(
                latitude = locationDataFlow.value.latitude,
                longitude = locationDataFlow.value.longitude

            ).collect { weatherData ->

                Log.d("debug_log", weatherData.toString())

                this@HomeScreenViewModel.weatherData = weatherData

                if (storeDataLocally) {
                    Log.d("debug_log", "Saving weather data...")
                    saveWeatherDataUseCase(weatherData, tripUUID.toString())
                }
            }
        }
    }

    private fun collectTrafficData(): Job {
        return viewModelScope.launch {
            while (true) {
                trafficData = getTrafficDataUseCase.invoke(
                    latitude = locationDataFlow.value.latitude,
                    longitude = locationDataFlow.value.longitude
                )

                delay(1000L)
            }
        }
    }

    // send driver data, so the web page can display the right information
    private fun getAndSendDriverData() {
        viewModelScope.launch(Dispatchers.IO) {
            val driverData = getDriverDataUseCase().first()
            sendDriverDataUseCase(driverData)
        }
    }

    fun retrieveDriverData() {
        viewModelScope.launch(Dispatchers.IO) {
            driverName = dataStoreRepository.retrieveUserName().first()
            carImageIndex = dataStoreRepository.retrieveCarImageIndex().first()
            avatarImageIndex = dataStoreRepository.retrieveAvatarImageIndex().first()
            vehicleType = getDriverDataUseCase().first().vehicleType
        }
    }

    fun saveLocalStorageState(uploadToServer: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            val storeLocally = !uploadToServer
            saveLocalStorageStateUseCase(storeLocally)
        }
    }

    fun getIsMeasuring() = isMeasuring.asStateFlow()

    fun getErrorMessages() = errorMessage.asStateFlow()

    fun getIsDeviceConnected() = bluetoothController.isConnected

    fun getOBDReadingDataFlow() = obdReadingData.asStateFlow()

    override fun onCleared() {
        stopLocationDataUpdateUseCase()

        readOBDDataJob?.cancel()
        readOBDDataJob = null
        bluetoothController.release()

        sendOrStoreDataJob?.cancel()
        sendOrStoreDataJob = null

        collectTrafficDataJob?.cancel()
        collectTrafficDataJob = null

        Log.d("debug_log", "HomeScreenViewModel::onCleared")
        super.onCleared()
    }
}