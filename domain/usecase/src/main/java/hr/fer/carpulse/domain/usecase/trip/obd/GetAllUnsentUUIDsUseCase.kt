package hr.fer.carpulse.domain.usecase.trip.obd

import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetAllUnsentUUIDsUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(): Flow<List<String>> {
        return tripsRepository.getAllUnsentUUIDs()
    }
}