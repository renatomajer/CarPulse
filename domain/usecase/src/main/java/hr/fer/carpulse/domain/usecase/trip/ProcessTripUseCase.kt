package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * UseCase for making an API request to the assistant to process an individual trip and generate trip
 * overview with advanced statistics. The overview is stored in the remote database.
 */
class ProcessTripUseCase(
    private val assistantRepository: AssistantRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(tripUUID: String): Boolean {
        return withContext(dispatcher) {
            assistantRepository.processTrip(tripUUID)
        }
    }
}