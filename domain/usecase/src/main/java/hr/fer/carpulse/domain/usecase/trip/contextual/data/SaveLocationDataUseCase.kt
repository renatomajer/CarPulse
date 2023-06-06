package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SaveLocationDataUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend operator fun invoke(locationData: LocationData, tripUUID: String) {
        tripsRepository.insertLocationData(locationData, tripUUID)
    }
}