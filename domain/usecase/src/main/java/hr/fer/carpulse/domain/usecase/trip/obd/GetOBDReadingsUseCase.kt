package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetOBDReadingsUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripUUID: String): Flow<List<OBDReading>> {
        return tripsRepository.getOBDReadings(tripUUID)
    }
}