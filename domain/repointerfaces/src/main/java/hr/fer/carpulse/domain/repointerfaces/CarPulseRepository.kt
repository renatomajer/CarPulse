package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.common.trip.TripDetails

interface CarPulseRepository {

    suspend fun getTripDistance(tripUUID: String): Int?

    suspend fun calculateTripDistance(tripUUID: String, locationData: List<LocationData>): Int?

    suspend fun getTripCoordinates(tripUUID: String): List<Coordinate>?

    suspend fun getDriverStatistics(driverId: String): DriverStatistics?

    suspend fun getTripDetails(tripUUID: String): TripDetails?
}