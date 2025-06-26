package hr.fer.carpulse.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class DriverStatisticsDto(
    val driverId: String,
    val totalDistance: Double,
    val totalDuration: Double,
    val averageSpeed: Double,
    val averageRpm: Double,
    val maxSpeed: Int,
    val maxRpm: Int,
    val drivingWithinSpeedLimit: Double,
    val drivingAboveSpeedLimit: Double
)