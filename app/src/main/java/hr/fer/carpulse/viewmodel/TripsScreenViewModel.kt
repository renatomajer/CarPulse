package hr.fer.carpulse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import hr.fer.carpulse.domain.usecase.trip.GetAllTripSummariesUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SendTripReadingDataUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetAllUnsentUUIDsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetOBDReadingsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.UpdateSummarySentStatusUseCase
import hr.fer.carpulse.domain.usecase.trip.review.DeleteTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.review.GetTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.DeleteTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.GetTripStartInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TripsScreenViewModel(
    private val getAllTripSummariesUseCase: GetAllTripSummariesUseCase,
    private val getAllUnsentUUIDsUseCase: GetAllUnsentUUIDsUseCase,
    private val getOBDReadingsUseCase: GetOBDReadingsUseCase,
    private val sendTripStartInfoUseCase: SendTripStartInfoUseCase,
    private val getTripStartInfoUseCase: GetTripStartInfoUseCase,
    private val deleteTripStartInfoUseCase: DeleteTripStartInfoUseCase,
    private val getTripReviewUseCase: GetTripReviewUseCase,
    private val sendTripReviewUseCase: SendTripReviewUseCase,
    private val deleteTripReviewUseCase: DeleteTripReviewUseCase,
    private val getSavedLocationDataUseCase: GetSavedLocationDataUseCase,
    private val updateSummarySentStatusUseCase: UpdateSummarySentStatusUseCase,
    private val getSavedWeatherDataUseCase: GetSavedWeatherDataUseCase,
    private val connectToBrokerUseCase: ConnectToBrokerUseCase,
    private val disconnectFromBrokerUseCase: DisconnectFromBrokerUseCase,
    private val sendTripReadingDataUseCase: SendTripReadingDataUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val sendDriverDataUseCase: SendDriverDataUseCase
) : ViewModel() {

    val tripSummaries = getAllTripSummariesUseCase()

    init {
        connectToBrokerUseCase()
    }

    fun sendAll() {
        viewModelScope.launch(Dispatchers.IO) {
            // get the uuids of all the trips that are stored locally
            val uuids = getAllUnsentUUIDsUseCase().first()

            // send driver data, so the web page can display the right information
            val driverData = getDriverDataUseCase().first()
            sendDriverDataUseCase(driverData)

            uuids.forEach { uuid ->

                // send trip start info and remove it from database
                val tripStartInfo = getTripStartInfoUseCase(uuid).first()
                sendTripStartInfoUseCase(tripStartInfo)
                deleteTripStartInfoUseCase(tripStartInfo)

                val weatherData = getSavedWeatherDataUseCase(uuid).first()

                val readings =
                    getOBDReadingsUseCase(uuid).first() // get all the readings with the specified uuid

                val locationDataList =
                    getSavedLocationDataUseCase(uuid).first() // get all location data from the trip

                // last known location data
                var lastLocationData = locationDataList.firstOrNull() ?: LocationData()

                var readingIndex = 0
                var currentReading = OBDReading()

                locationDataList.forEach { locationData ->


                    if (readingIndex < readings.size && locationData.timestamp >= readings[readingIndex].timestamp) {
                        currentReading = readings[readingIndex]
                        readingIndex += 1
                    }

                    // send location , weather and reading to server and delete the data from database
                    sendTripReadingDataUseCase(locationData, weatherData, uuid, currentReading)

                    delay(800L) // simulate the time gap between sending the data
                }

                // send trip review and remove it from database
                val tripReview = getTripReviewUseCase(uuid).first()
                sendTripReviewUseCase(tripReview)
                deleteTripReviewUseCase(tripReview)

                // all the data is sent to the server - mark trip summary as sent
                updateSummarySentStatusUseCase(uuid, true)
            }
        }
    }

    override fun onCleared() {
        disconnectFromBrokerUseCase()
        super.onCleared()
    }
}