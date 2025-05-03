package hr.fer.carpulse.domain.common.contextual.data

import kotlinx.serialization.Serializable

@Serializable
data class TrafficData(
    val timestamp: Long,
    val flowSegmentData: FlowSegmentData
)

@Serializable
data class FlowSegmentData(
    val frc: String,
    val currentSpeed: Int,
    val freeFlowSpeed: Int,
    val currentTravelTime: Int,
    val freeFlowTravelTime: Int,
    val confidence: Double,
    val roadClosure: Boolean,
    val coordinates: Coordinates
)

@Serializable
data class Coordinates(
    val coordinate: List<Coordinate>
)

@Serializable
data class Coordinate(
    val latitude: Double,
    val longitude: Double
)