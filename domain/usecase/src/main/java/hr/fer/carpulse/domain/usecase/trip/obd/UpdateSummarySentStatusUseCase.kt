package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class UpdateSummarySentStatusUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripUUID: String, sent: Boolean) {
        tripsRepository.updateSummarySentStatus(tripUUID, sent)
    }
}