package hr.fer.carpulse.domain.usecase.trip.review

import hr.fer.carpulse.domain.common.trip.TripReview
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import kotlinx.coroutines.flow.Flow

class GetTripReviewUseCase(
    private val driverDataRepository: DriverDataRepository
) {
    operator fun invoke(tripUUID: String): Flow<TripReview> {
        return driverDataRepository.getTripReview(tripUUID)
    }
}