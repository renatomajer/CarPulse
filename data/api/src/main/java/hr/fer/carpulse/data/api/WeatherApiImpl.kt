package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import io.ktor.client.HttpClient
import io.ktor.client.request.get

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
        return API_BASE_URL + "?lat=${lat}&lon=${lon}" + "&appid=${BuildConfig.WEATHER_API_KEY}" + "&units=metric"
    }

    companion object {
        private const val API_BASE_URL =
            "https://api.openweathermap.org/data/2.5/weather"
    }
}