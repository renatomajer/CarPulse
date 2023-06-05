package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.StateFlow

class GetLocationDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(): StateFlow<LocationData> {
        return tripsRepository.getLocationData()
    }
}