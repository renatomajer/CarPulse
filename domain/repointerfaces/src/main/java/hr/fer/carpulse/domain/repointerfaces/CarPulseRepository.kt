package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.contextual.data.LocationData

interface CarPulseRepository {

    suspend fun getTripDistance(tripUUID: String): Int?

    suspend fun calculateTripDistance(tripUUID: String, locationData: List<LocationData>): Int?
}