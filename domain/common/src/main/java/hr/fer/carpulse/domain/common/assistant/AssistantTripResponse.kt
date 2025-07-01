package hr.fer.carpulse.domain.common.assistant

import kotlinx.serialization.Serializable


@Serializable
data class AssistantTripResponse(
    val response: String
)
