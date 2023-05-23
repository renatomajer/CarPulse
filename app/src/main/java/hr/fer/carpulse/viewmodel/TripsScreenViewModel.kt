package hr.fer.carpulse.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.usecase.trip.GetAllTripSummariesUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetAllUnsentUUIDsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetOBDReadingsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.SendOBDReadingUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.UpdateSummarySentStatusUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TripsScreenViewModel(
    private val getAllTripSummariesUseCase: GetAllTripSummariesUseCase,
    private val getAllUnsentUUIDsUseCase: GetAllUnsentUUIDsUseCase,
    private val getOBDReadingsUseCase: GetOBDReadingsUseCase,
    private val sendOBDReadingUseCase: SendOBDReadingUseCase,
    private val updateSummarySentStatusUseCase: UpdateSummarySentStatusUseCase
) : ViewModel() {

    val tripSummaries = getAllTripSummariesUseCase()

    fun sendAll() {
        viewModelScope.launch(Dispatchers.IO) {
            // get the uuids of all the trips that are stored locally
            val uuids = getAllUnsentUUIDsUseCase().first()

            uuids.forEach { uuid ->
                val readings =
                    getOBDReadingsUseCase(uuid).first() // get all the readings with the specified uuid

                readings.forEach { obdReading ->
                    // send reading to server and delete the entry from database
                    sendOBDReadingUseCase(obdReading, uuid)
                }

                // all the data is sent to the server - mark trip summary as sent
                updateSummarySentStatusUseCase(uuid, true)
            }

        }
    }
}