package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetSavedLocationDataUseCase(
    private val tripsRepository: TripsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(tripUUID: String): Flow<List<LocationData>> {
        return tripsRepository.getSavedLocationData(tripUUID).flowOn(dispatcher)
    }
}