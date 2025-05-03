package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class TrafficApiImpl(
    private val httpClient: HttpClient
) : TrafficApi {

    override suspend fun getTrafficFlowData(
        zoom: Int,
        latitude: Double,
        longitude: Double
    ): TrafficData? {
        return try {
            val url = getUrl(zoom = zoom, lat = latitude, lon = longitude)
            val trafficData = httpClient.get<TrafficData>(url)
            trafficData.copy(timestamp = System.currentTimeMillis())
        } catch (exc: Exception) {
            Log.d("debug_log", exc.message ?: "Exception occurred wile contacting TomTom server.")
            null
        }
    }

    private fun getUrl(zoom: Int, lat: Double, lon: Double): String {
        return "$API_BASE_URL/$zoom/json?point=$lat,$lon&unit=KMPH&openLr=false&key=${BuildConfig.TOMTOM_API_KEY}"
    }

    companion object {
        private const val API_BASE_URL =
            "https://api.tomtom.com/traffic/services/4/flowSegmentData/relative0"
    }
}