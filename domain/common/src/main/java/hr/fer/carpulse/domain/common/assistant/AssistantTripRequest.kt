package hr.fer.carpulse.domain.common.assistant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AssistantTripRequest(
    @SerialName("user_id")
    val userId: String,
    @SerialName("trip_id")
    val tripId: String
)
