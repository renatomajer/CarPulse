package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class UpdateLocationDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke() {
        tripsRepository.updateLocationData()
    }
}