package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SendOBDReadingUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(obdReading: OBDReading, tripUUID: String) {
        // send the reading to server and delete the entry from database
        tripsRepository.sendOBDReading(obdReading, tripUUID)
    }
}