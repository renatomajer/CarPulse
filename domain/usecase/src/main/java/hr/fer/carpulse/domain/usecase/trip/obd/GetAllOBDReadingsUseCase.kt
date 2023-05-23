package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetAllOBDReadingsUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(): Flow<List<OBDReading>> {
        return tripsRepository.getAllOBDReadings()
    }
}