package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetWeatherDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(latitude: Double, longitude: Double): Flow<WeatherData> {
        return tripsRepository.getWeatherData(latitude = latitude, longitude = longitude)
    }
}