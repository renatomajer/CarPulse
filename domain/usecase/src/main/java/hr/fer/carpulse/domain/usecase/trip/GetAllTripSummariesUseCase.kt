package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetAllTripSummariesUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(): Flow<List<TripSummary>> {
        return tripsRepository.getTripSummaries()
    }
}