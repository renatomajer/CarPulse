package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Gets the trip route.
 * Retrieves the route coordinates from the CarPulse backend.
 *
 * @param tripUUID the UUID of the trip uploaded to CarPulse server.
 * @return [List]<[Coordinate]>
 */
class GetTripRouteUseCase(
    private val carPulseRepository: CarPulseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(tripUUID: String): List<Coordinate>? {
        return withContext(dispatcher) {
            carPulseRepository.getTripCoordinates(tripUUID)
        }
    }
}