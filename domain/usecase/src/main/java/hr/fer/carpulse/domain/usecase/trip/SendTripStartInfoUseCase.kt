package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.trip.TripStartInfo
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SendTripStartInfoUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripStartInfo: TripStartInfo) {
        tripsRepository.sendTripStartInfo(tripStartInfo)
    }
}