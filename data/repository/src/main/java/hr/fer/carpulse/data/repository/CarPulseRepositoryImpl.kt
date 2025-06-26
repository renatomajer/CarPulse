package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.CarPulseApi
import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.driver.DriverStatistics
import hr.fer.carpulse.domain.repointerfaces.CarPulseRepository

class CarPulseRepositoryImpl(
    private val carPulseApi: CarPulseApi
) : CarPulseRepository {

    override suspend fun getTripDistance(tripUUID: String): Int? {
        return carPulseApi.getTripDistance(tripUUID).distance
    }

    override suspend fun calculateTripDistance(
        tripUUID: String,
        locationData: List<LocationData>
    ): Int? {
        return carPulseApi.calculateTripDistance(tripUUID, locationData).distance
    }

    override suspend fun getTripCoordinates(tripUUID: String): List<Coordinate>? {
        return carPulseApi.getTripCoordinates(tripUUID)?.coordinates
    }

    override suspend fun getDriverStatistics(driverId: String): DriverStatistics? {
        val driverStatisticsDto = carPulseApi.getDriverStatistics(driverId)

        return if (driverStatisticsDto != null) {
            DriverStatistics(
                driverId = driverStatisticsDto.driverId,
                totalDistance = driverStatisticsDto.totalDistance.toInt(),
                totalDuration = driverStatisticsDto.totalDuration.toInt(),
                averageSpeed = driverStatisticsDto.averageSpeed.toInt(),
                averageRpm = driverStatisticsDto.averageRpm.toInt(),
                drivingWithinSpeedLimit = driverStatisticsDto.drivingWithinSpeedLimit.toInt(),
                drivingAboveSpeedLimit = driverStatisticsDto.drivingAboveSpeedLimit.toInt(),
                maxSpeed = driverStatisticsDto.maxSpeed,
                maxRpm = driverStatisticsDto.maxRpm
            )
        } else {
            null
        }
    }
}