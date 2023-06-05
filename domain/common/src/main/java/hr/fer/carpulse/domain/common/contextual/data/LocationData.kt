package hr.fer.carpulse.domain.common.contextual.data

import kotlinx.serialization.Transient

@kotlinx.serialization.Serializable
data class LocationData(
    val altitude: Double = 0.0,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    @Transient
    val timestamp: Long = 0L
) {
}