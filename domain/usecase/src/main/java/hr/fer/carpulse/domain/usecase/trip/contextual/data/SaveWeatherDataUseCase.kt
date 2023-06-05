package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SaveWeatherDataUseCase(
    private val tripsRepository: TripsRepository
) {
    suspend operator fun invoke(weatherData: WeatherData, tripUUID: String) {
        tripsRepository.insertWeatherData(weatherData, tripUUID)
    }
}