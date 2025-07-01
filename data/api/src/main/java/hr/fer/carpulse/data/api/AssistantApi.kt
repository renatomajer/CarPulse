package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.assistant.AssistantQuestionRequest
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionResponse
import hr.fer.carpulse.domain.common.assistant.AssistantStatisticsRequest
import hr.fer.carpulse.domain.common.assistant.AssistantStatisticsResponse
import hr.fer.carpulse.domain.common.assistant.AssistantTripRequest
import hr.fer.carpulse.domain.common.assistant.AssistantTripResponse

interface AssistantApi {

    suspend fun askAssistant(
        assistantQuestionRequest: AssistantQuestionRequest
    ): AssistantQuestionResponse?

    suspend fun processTrip(tripUUID: String): Boolean

    suspend fun getTripOverview(assistantTripRequest: AssistantTripRequest): AssistantTripResponse?

    suspend fun getOverallStatisticsOverview(
        assistantStatisticsRequest: AssistantStatisticsRequest
    ): AssistantStatisticsResponse?
}