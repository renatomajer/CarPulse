package hr.fer.carpulse.domain.usecase.driver

import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository

class SendTripReviewUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    operator fun invoke(tripReview: TripReview) {
        driverDataRepository.sendTripReview(tripReview)
    }
}