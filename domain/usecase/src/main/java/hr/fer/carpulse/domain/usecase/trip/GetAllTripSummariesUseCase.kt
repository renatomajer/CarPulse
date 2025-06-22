package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetAllTripSummariesUseCase(
    private val tripsRepository: TripsRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(): Flow<List<TripSummary>> {
        return tripsRepository.getTripSummaries().flowOn(dispatcher)
    }
}