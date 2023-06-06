package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SaveTripSummaryUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend operator fun invoke(tripSummary: TripSummary) {
        tripsRepository.insertTripSummary(tripSummary)
    }
}