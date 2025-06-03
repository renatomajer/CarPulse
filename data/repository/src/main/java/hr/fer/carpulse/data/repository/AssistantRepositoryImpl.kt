package hr.fer.carpulse.data.repository

import hr.fer.carpulse.data.api.AssistantApi
import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.common.assistant.AssistantResponse
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository

class AssistantRepositoryImpl(
    private val assistantApi: AssistantApi
) : AssistantRepository {
    override suspend fun askAssistant(assistantRequest: AssistantRequest): AssistantResponse? =
        assistantApi.askAssistant(assistantRequest)
}