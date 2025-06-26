package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.trip.TripDetails
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTripDetailsUseCase(
    private val carPulseRepository: CarPulseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(tripUUID: String): TripDetails? {
        return withContext(dispatcher) {
            val tripDetails = carPulseRepository.getTripDetails(tripUUID)

            var formattedTripDetails: TripDetails? = null

            tripDetails?.let {
                formattedTripDetails = it.copy(
                    distance = String.format("%.1f", it.distance).toDouble(),
                    idlingPct = String.format("%.2f", it.idlingPct).toDouble(),
                )
            }

            formattedTripDetails
        }
    }
}