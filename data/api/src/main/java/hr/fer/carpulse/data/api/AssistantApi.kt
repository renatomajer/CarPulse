package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.common.assistant.AssistantResponse

interface AssistantApi {

    suspend fun askAssistant(assistantRequest: AssistantRequest): AssistantResponse?

    suspend fun processTrip(tripUUID: String): Boolean
}