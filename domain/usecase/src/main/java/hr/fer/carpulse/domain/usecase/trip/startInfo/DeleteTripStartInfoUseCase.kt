package hr.fer.carpulse.domain.usecase.trip.startInfo

import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class DeleteTripStartInfoUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripStartInfo: TripStartInfo) {
        tripsRepository.deleteTripStartInfo(tripStartInfo)
    }
}