package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSavedTrafficDataUseCase(
    private val tripsRepository: TripsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(tripUUID: String): Flow<List<TrafficData>> {
        return tripsRepository.getSavedTrafficData(tripUUID).flowOn(dispatcher)
    }
}