package hr.fer.carpulse.data.api.model

import kotlinx.serialization.Serializable

@Serializable
data class TripDistance(
    val tripUUID: String,
    val distance: Int?
)
