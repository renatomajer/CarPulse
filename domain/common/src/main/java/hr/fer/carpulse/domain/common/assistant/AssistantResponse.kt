package hr.fer.carpulse.domain.common.assistant

import kotlinx.serialization.Serializable

@Serializable
data class AssistantResponse(
    val intent: String,
    val response: String
)
