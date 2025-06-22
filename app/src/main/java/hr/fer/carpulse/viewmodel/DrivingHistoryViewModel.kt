package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import hr.fer.carpulse.domain.usecase.trip.GetAllTripSummariesUseCase
import hr.fer.carpulse.domain.usecase.trip.GetTripDistanceUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SendTripReadingDataUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetAllUnsentUUIDsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetOBDReadingsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.UpdateSummarySentStatusUseCase
import hr.fer.carpulse.domain.usecase.trip.review.DeleteTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.review.GetTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.DeleteTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.GetTripStartInfoUseCase
import hr.fer.carpulse.ui.model.TripSummaryUIModel
import hr.fer.carpulse.ui.model.TripUploadState
import hr.fer.carpulse.util.calculateDurationInMinutes
import hr.fer.carpulse.util.getDate
import hr.fer.carpulse.util.getTime
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DrivingHistoryViewModel(
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
    private val getSavedTrafficDataUseCase: GetSavedTrafficDataUseCase,
    private val connectToBrokerUseCase: ConnectToBrokerUseCase,
    private val disconnectFromBrokerUseCase: DisconnectFromBrokerUseCase,
    private val sendTripReadingDataUseCase: SendTripReadingDataUseCase,
    private val getDriverDataUseCase: GetDriverDataUseCase,
    private val sendDriverDataUseCase: SendDriverDataUseCase,
    private val getTripDistanceUseCase: GetTripDistanceUseCase
) : ViewModel() {

    var tripSummaries by mutableStateOf<List<TripSummaryUIModel>>(emptyList())
        private set

    var isLoading by mutableStateOf(true)
        private set

    init {
        connectToBrokerUseCase()

        loadTripSummaries()

        //TODO: check if flow collection blocks the UI
        viewModelScope.launch {
            getAllTripSummariesUseCase().cancellable().collect { localTripSummary ->
                val sentTripsUUIDs = localTripSummary.filter { it.sent }.map { it.tripUUID }

                val tripsMarkedAsNotSent =
                    tripSummaries.filter { it.uploadState != TripUploadState.Uploaded }

                val tripsToUpdate =
                    tripsMarkedAsNotSent.filter { sentTripsUUIDs.contains(it.tripUUID) }
                        .map { it.tripUUID }

                val updatedList = tripSummaries.toMutableList()
                updatedList.replaceAll {
                    if (tripsToUpdate.contains(it.tripUUID)) {
                        it.copy(uploadState = TripUploadState.Uploaded)
                    } else {
                        it
                    }
                }

                tripSummaries = updatedList
            }
        }
    }

    fun sendAll() {
        viewModelScope.launch(Dispatchers.IO) {
            // get the uuids of all the trips that are stored locally
            val uuids = getAllUnsentUUIDsUseCase().first()

            // send driver data, so the web page can display the right information
            val driverData = getDriverDataUseCase().first()
            sendDriverDataUseCase(driverData)

            uuids.forEach { uuid ->

                // Mark trip as uploading
                val updatedList = tripSummaries.toMutableList()
                updatedList.replaceAll {
                    if (it.tripUUID == uuid) {
                        it.copy(uploadState = TripUploadState.IsUploading)
                    } else {
                        it
                    }
                }
                tripSummaries = updatedList

                // send trip start info and remove it from database
                val tripStartInfo = getTripStartInfoUseCase(uuid).first()
                sendTripStartInfoUseCase(tripStartInfo)
                deleteTripStartInfoUseCase(tripStartInfo)

                val weatherData = getSavedWeatherDataUseCase(uuid).first()

                val trafficDataList =
                    getSavedTrafficDataUseCase(uuid).first().sortedBy { it.timestamp }

                // Get all the readings with the specified uuid
                val readings =
                    getOBDReadingsUseCase(uuid).first().sortedBy { it.timestamp }

                // Get all location data from the trip
                val locationDataList =
                    getSavedLocationDataUseCase(uuid).first().sortedBy { it.timestamp }

                var readingIndex = 0
                var currentReading = OBDReading()
                var currentTrafficData: TrafficData? = null

                locationDataList.forEach { locationData ->


                    if (readingIndex < readings.size && locationData.timestamp >= readings[readingIndex].timestamp) {
                        currentReading = readings[readingIndex]
                        currentTrafficData = trafficDataList.getOrNull(readingIndex)
                        readingIndex += 1
                    }

                    // send location , weather and reading to server and delete the data from database
                    sendTripReadingDataUseCase(
                        locationData,
                        weatherData,
                        uuid,
                        currentReading,
                        currentTrafficData
                    )

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

    private fun loadTripSummaries() {
        viewModelScope.launch {
            tripSummaries = getAllTripSummariesUseCase().first().map {

                val tripLocationData = if (it.sent) {
                    null
                } else {
                    getSavedLocationDataUseCase(it.tripUUID).first()
                }

                TripSummaryUIModel(
                    tripUUID = it.tripUUID,
                    startDate = getDate(it.startTimestamp),
                    startTime = getTime(it.startTimestamp),
                    distance = getTripDistanceUseCase(it.tripUUID, tripLocationData) ?: 0.0,
                    duration = calculateDurationInMinutes(
                        startTimestamp = it.startTimestamp,
                        endTimestamp = it.endTimestamp
                    ),
                    uploadState = if (it.sent) TripUploadState.Uploaded else TripUploadState.NotUploaded
                )
            }

            isLoading = false
        }
    }

    override fun onCleared() {
        disconnectFromBrokerUseCase()
        super.onCleared()
    }
}