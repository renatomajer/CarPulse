package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.WeatherData

interface WeatherApi {
    suspend fun getWeatherInfo(latitude: Double, longitude: Double): WeatherData
}