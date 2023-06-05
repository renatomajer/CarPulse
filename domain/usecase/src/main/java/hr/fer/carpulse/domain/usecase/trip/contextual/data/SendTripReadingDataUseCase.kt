package hr.fer.carpulse.domain.usecase.trip.contextual.data

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.repointerfaces.TripsRepository

class SendTripReadingDataUseCase(
    private val tripsRepository: TripsRepository
) {
    operator fun invoke(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading
    ) {
        tripsRepository.sendTripReadingData(locationData, weatherData, tripUUID, obdReading)
    }
}