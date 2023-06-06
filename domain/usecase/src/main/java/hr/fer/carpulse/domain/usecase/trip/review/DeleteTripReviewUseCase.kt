package hr.fer.carpulse.domain.usecase.trip.review

import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository

class DeleteTripReviewUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    operator fun invoke(tripReview: TripReview) {
        driverDataRepository.deleteTripReview(tripReview)
    }
}