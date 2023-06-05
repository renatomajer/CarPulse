package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import io.ktor.client.*
import io.ktor.client.request.*

class WeatherApiImpl(
    private val client: HttpClient
) : WeatherApi {
    override suspend fun getWeatherInfo(latitude: Double, longitude: Double): WeatherData {
        return try {
            val url = getUrl(latitude, longitude)
            client.get<WeatherData>(url)
        } catch (exc: Exception) {
            WeatherData()
        }
    }

    private fun getUrl(lat: Double, lon: Double): String {
        return API_BASE_URL + "?lat=${lat}&lon=${lon}" + "&appid=$API_KEY" + "&units=metric"
    }

    companion object {
        private const val API_BASE_URL =
            "https://api.openweathermap.org/data/2.5/weather"
        private const val API_KEY = "fe0f16fd53fcba118c499b792c5f6e5a"
    }
}