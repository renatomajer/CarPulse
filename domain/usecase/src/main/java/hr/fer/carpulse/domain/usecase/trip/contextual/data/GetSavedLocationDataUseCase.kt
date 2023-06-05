package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedLocationDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripUUID: String): Flow<List<LocationData>> {
        return tripsRepository.getSavedLocationData(tripUUID)
    }
}