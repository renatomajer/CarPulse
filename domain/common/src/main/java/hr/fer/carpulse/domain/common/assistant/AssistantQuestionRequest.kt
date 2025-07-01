package hr.fer.carpulse.domain.common.assistant

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AssistantQuestionRequest(
    val question: String,
    @SerialName("user_id")
    val userId: String
)