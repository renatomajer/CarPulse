package hr.fer.carpulse.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.domain.common.trip.TripDetails
import hr.fer.carpulse.domain.usecase.trip.GetTripDetailsUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetTripRouteUseCase
import kotlinx.coroutines.launch

class TripDetailsViewModel(
    private val getTripRouteUseCase: GetTripRouteUseCase,
    private val getTripDetailsUseCase: GetTripDetailsUseCase
) : ViewModel() {

    var tripCoordinates by mutableStateOf<List<Coordinate>?>(null)
        private set

    var isLoading by mutableStateOf(true)
        private set

    var tripDetails by mutableStateOf<TripDetails?>(null)
        private set

    fun getTripCoordinates(tripUUID: String) {
        viewModelScope.launch {
            tripCoordinates = getTripRouteUseCase(tripUUID)
        }
    }

    fun getTripDetails(tripUUID: String) {
        viewModelScope.launch {
            tripDetails = getTripDetailsUseCase(tripUUID)
            isLoading = false
        }
    }

    fun getAssistantAnalysis(tripUUID: String) {
        // TODO: implement assistant call and play response
    }
}