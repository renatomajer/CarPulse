package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.assistant.AssistantQuestionRequest
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionResponse

interface AssistantRepository {
    suspend fun askAssistant(assistantQuestionRequest: AssistantQuestionRequest): AssistantQuestionResponse?

    suspend fun processTrip(tripUUID: String): Boolean

    suspend fun getTripOverview(userId: String, tripId: String): String?

    suspend fun getOverallStatisticsOverview(userId: String): String?
}