package hr.fer.carpulse.domain.usecase.trip.startInfo

import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SaveTripStartInfoUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend operator fun invoke(tripStartInfo: TripStartInfo) {
        tripsRepository.insertTripStartInfo(tripStartInfo)
    }
}