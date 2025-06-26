package hr.fer.carpulse.domain.common.trip

data class TripDetails(
    val tripId: String,
    val distance: Double,
    val startAddress: String,
    val endAddress: String,
    val weatherDescription: String?,
    val weatherTemperature: Int?,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val duration: Int,
    val idlingPct: Double,
    val idlingTime: Double,
    val avgSpeed: Int,
    val maxSpeed: Int,
    val avgRpm: Int,
    val maxRpm: Int,
    val drivingAboveSpeedLimit: Int,
    val drivingWithinSpeedLimit: Int,
    val suddenBreaking: Int,
    val suddenAcceleration: Int,
    val stopAndGoSituations: Int
)
