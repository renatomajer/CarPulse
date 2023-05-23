package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SaveOBDReadingUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend operator fun invoke(obdReading: OBDReading, tripUUID: String) {
        tripsRepository.insertOBDReading(obdReading, tripUUID)
    }
}