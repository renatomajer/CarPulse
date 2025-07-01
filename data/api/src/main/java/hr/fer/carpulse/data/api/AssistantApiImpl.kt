package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionRequest
import hr.fer.carpulse.domain.common.assistant.AssistantQuestionResponse
import hr.fer.carpulse.domain.common.assistant.AssistantStatisticsRequest
import hr.fer.carpulse.domain.common.assistant.AssistantStatisticsResponse
import hr.fer.carpulse.domain.common.assistant.AssistantTripRequest
import hr.fer.carpulse.domain.common.assistant.AssistantTripResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post

class AssistantApiImpl(private val httpClient: HttpClient) : AssistantApi {

    override suspend fun askAssistant(assistantQuestionRequest: AssistantQuestionRequest): AssistantQuestionResponse? {
        return try {
            val response =
                httpClient.post<AssistantQuestionResponse>("${BuildConfig.ASSISTANT_BASE_URL}/ask") {
                    body = assistantQuestionRequest
                }
            Log.d("debug_log", response.toString())
            response
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting Assistant.")
            null
        }
    }

    override suspend fun processTrip(tripUUID: String): Boolean {
        return try {
            httpClient.post<Unit>("${BuildConfig.ASSISTANT_BASE_URL}/process-trip/$tripUUID")
            true
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting Assistant.")
            false
        }
    }

    override suspend fun getTripOverview(assistantTripRequest: AssistantTripRequest): AssistantTripResponse? {
        return try {
            val response =
                httpClient.post<AssistantTripResponse>("${BuildConfig.ASSISTANT_BASE_URL}/ask") {
                    body = assistantTripRequest
                }
            Log.d("debug_log", response.toString())
            response
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting Assistant.")
            null
        }
    }

    override suspend fun getOverallStatisticsOverview(assistantStatisticsRequest: AssistantStatisticsRequest): AssistantStatisticsResponse? {
        return try {
            val response =
                httpClient.post<AssistantStatisticsResponse>("${BuildConfig.ASSISTANT_BASE_URL}/ask") {
                    body = assistantStatisticsRequest
                }
            Log.d("debug_log", response.toString())
            response
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting Assistant.")
            null
        }
    }
}