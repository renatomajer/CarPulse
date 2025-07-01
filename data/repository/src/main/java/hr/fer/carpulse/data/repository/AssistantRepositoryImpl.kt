package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.AssistantApi
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionRequest
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionResponse
import hr.fer.carpulse.domain.common.assistant.AssistantStatisticsRequest
import hr.fer.carpulse.domain.common.assistant.AssistantTripRequest
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository

class AssistantRepositoryImpl(
    private val assistantApi: AssistantApi
) : AssistantRepository {
    override suspend fun askAssistant(assistantQuestionRequest: AssistantQuestionRequest): AssistantQuestionResponse? =
        assistantApi.askAssistant(assistantQuestionRequest)

    override suspend fun processTrip(tripUUID: String): Boolean =
        assistantApi.processTrip(tripUUID)

    override suspend fun getTripOverview(userId: String, tripId: String): String? =
        assistantApi.getTripOverview(
            AssistantTripRequest(
                userId = userId,
                tripId = tripId
            )
        )?.response

    override suspend fun getOverallStatisticsOverview(userId: String): String? =
        assistantApi.getOverallStatisticsOverview(
            AssistantStatisticsRequest(
                userId = userId,
                user = true
            )
        )?.response
}