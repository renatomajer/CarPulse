package hr.fer.carpulse.domain.repointerfaces

import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.common.assistant.AssistantResponse

interface AssistantRepository {
    suspend fun askAssistant(assistantRequest: AssistantRequest): AssistantResponse?

    suspend fun processTrip(tripUUID: String): Boolean
}