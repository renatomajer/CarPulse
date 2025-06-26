package hr.fer.carpulse.data.api.model

import kotlinx.serialization.Serializable


@Serializable
data class TripDetailsDto(
    val tripId: String,
    val distance: Double,
    val startAddress: String,
    val endAddress: String,
    val weather: TripWeatherDto,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val duration: Double,
    val idlingPct: Double,
    val idlingTime: Double,
    val avgSpeed: Double,
    val maxSpeed: Int,
    val avgRpm: Double,
    val maxRpm: Int,
    val drivingAboveSpeedLimit: Double,
    val drivingWithinSpeedLimit: Double,
    val suddenBreaking: Int,
    val suddenAcceleration: Int,
    val stopAndGo: Int
)