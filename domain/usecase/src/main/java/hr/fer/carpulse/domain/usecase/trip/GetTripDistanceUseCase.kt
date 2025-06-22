package hr.fer.carpulse.domain.usecase.trip

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTripDistanceUseCase(
    private val carPulseRepository: CarPulseRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(tripUUID: String, locationData: List<LocationData>?): Double? {
        return withContext(dispatcher) {
            val distanceInMeters = if (locationData == null) {
                carPulseRepository.getTripDistance(tripUUID)
            } else {
                carPulseRepository.calculateTripDistance(tripUUID, locationData)
            }
            val distanceInKm: Double? = if(distanceInMeters != null) {
                distanceInMeters / 1000.0
            } else {
                null
            }

            if(distanceInKm != null) {
                String.format("%.1f", distanceInKm).toDouble()
            } else {
                null
            }
        }
    }
}