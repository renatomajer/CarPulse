package hr.fer.carpulse.domain.common.driver

data class DriverStatistics(
    val driverId: String,
    val totalDistance: Int,
    val totalDuration: Int,
    val averageSpeed: Int,
    val averageRpm: Int,
    val maxSpeed: Int,
    val maxRpm: Int,
    val drivingWithinSpeedLimit: Int,
    val drivingAboveSpeedLimit: Int
)
