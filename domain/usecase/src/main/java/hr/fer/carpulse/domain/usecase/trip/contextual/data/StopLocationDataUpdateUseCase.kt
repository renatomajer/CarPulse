package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class StopLocationDataUpdateUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke() {
        tripsRepository.stopLocationUpdate()
    }
}