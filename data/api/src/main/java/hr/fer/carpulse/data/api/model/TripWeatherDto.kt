package hr.fer.carpulse.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TripWeatherDto(
    val description: String?,
    val temperature: Double?
)