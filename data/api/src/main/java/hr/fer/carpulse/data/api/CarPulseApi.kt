package hr.fer.carpulse.data.api

import hr.fer.carpulse.data.api.model.DriverStatisticsDto
import hr.fer.carpulse.data.api.model.TripCoordinatesDto
import hr.fer.carpulse.data.api.model.TripDetailsDto
import hr.fer.carpulse.data.api.model.TripDistanceDto
import hr.fer.carpulse.domain.common.contextual.data.LocationData

interface CarPulseApi {

    suspend fun getTripDistance(tripUUID: String): TripDistanceDto

    suspend fun calculateTripDistance(tripUUID: String, locationData: List<LocationData>): TripDistanceDto

    suspend fun getTripCoordinates(tripUUID: String): TripCoordinatesDto?

    suspend fun getDriverStatistics(driverId: String): DriverStatisticsDto?

    suspend fun getTripDetailsDto(tripUUID: String): TripDetailsDto?
}