package hr.fer.carpulse.domain.usecase.trip.startInfo

import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetTripStartInfoUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripUUID: String): Flow<TripStartInfo> {
        return tripsRepository.getTripStartInfo(tripUUID)
    }
}