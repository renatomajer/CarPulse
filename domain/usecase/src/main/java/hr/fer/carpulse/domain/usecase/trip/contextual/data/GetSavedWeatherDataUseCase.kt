package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedWeatherDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(tripUUID: String): Flow<WeatherData> {
        return tripsRepository.getSavedWeatherData(tripUUID)
    }
}