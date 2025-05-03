package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SaveTrafficDataUseCase(
    private val tripsRepository: TripsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(trafficData: TrafficData, tripUUID: String) {
        withContext(dispatcher) {
            tripsRepository.insertTrafficData(trafficData, tripUUID)
        }
    }
}