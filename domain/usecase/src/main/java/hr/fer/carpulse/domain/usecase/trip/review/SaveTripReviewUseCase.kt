package hr.fer.carpulse.domain.usecase.trip.review

import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository

class SaveTripReviewUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    suspend operator fun invoke(tripReview: TripReview) {
        driverDataRepository.insertTripReview(tripReview)
    }
}