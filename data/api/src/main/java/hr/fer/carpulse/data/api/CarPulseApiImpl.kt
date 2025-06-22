package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.data.api.model.TripDistance
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post

class CarPulseApiImpl(
    private val httpClient: HttpClient
): CarPulseApi {

    override suspend fun getTripDistance(tripUUID: String): TripDistance {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/data/distance"
            val tripDistance = httpClient.get<TripDistance>(url)
            tripDistance
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            TripDistance(tripUUID, null)
        }
    }

    override suspend fun calculateTripDistance(tripUUID: String, locationData: List<LocationData>): TripDistance {
        return try {
            val url = "$API_BASE_URL/trips/$tripUUID/calculate/distance"
            val tripDistance = httpClient.post<TripDistance>(url) {
                body = locationData
            }
            tripDistance
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting CarPulse server.")
            TripDistance(tripUUID, null)
        }
    }

    companion object {
        private const val API_BASE_URL =
            "http://31.147.204.134:4000"
    }
}