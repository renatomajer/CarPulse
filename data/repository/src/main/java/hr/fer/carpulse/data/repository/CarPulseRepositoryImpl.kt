package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.CarPulseApi
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository

class CarPulseRepositoryImpl(
    private val carPulseApi: CarPulseApi
): CarPulseRepository {

    override suspend fun getTripDistance(tripUUID: String): Int? {
        return carPulseApi.getTripDistance(tripUUID).distance
    }

    override suspend fun calculateTripDistance(
        tripUUID: String,
        locationData: List<LocationData>
    ): Int? {
        return carPulseApi.calculateTripDistance(tripUUID, locationData).distance
    }
}