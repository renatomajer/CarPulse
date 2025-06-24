package hr.fer.carpulse.data.api.model

import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import kotlinx.serialization.Serializable

@Serializable
data class TripCoordinates(
    val tripUUID: String,
    val coordinates: List<Coordinate>
)


