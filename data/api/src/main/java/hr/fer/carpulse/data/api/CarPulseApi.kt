package hr.fer.carpulse.data.api

import hr.fer.carpulse.data.api.model.TripDistance
import hr.fer.carpulse.domain.common.contextual.data.LocationData

interface CarPulseApi {

    suspend fun getTripDistance(tripUUID: String): TripDistance

    suspend fun calculateTripDistance(tripUUID: String, locationData: List<LocationData>): TripDistance
}