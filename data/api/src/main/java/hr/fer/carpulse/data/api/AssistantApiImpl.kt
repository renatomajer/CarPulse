package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.assistant.AssistantRequest
import hr.fer.carpulse.domain.common.assistant.AssistantResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AssistantApiImpl(private val httpClient: HttpClient) : AssistantApi {

    override suspend fun askAssistant(assistantRequest: AssistantRequest): AssistantResponse? {
        return try {
            val response =
                httpClient.post<AssistantResponse>("${BuildConfig.ASSISTANT_BASE_URL}/ask") {
                    body = assistantRequest
                }
            Log.d("debug_log", response.toString())
            response
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting Assistant.")
            null
        }
    }
}