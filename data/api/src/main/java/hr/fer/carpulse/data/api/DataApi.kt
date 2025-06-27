package hr.fer.carpulse.data.api

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripStartInfo

interface DataApi {

    fun connectToMqttBroker()

    fun disconnectFromMqttBroker()

    fun sendDriverData(driverData: DriverData)

    fun sendTripStartInfo(tripStartInfo: TripStartInfo)

    fun sendTripReadingData(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading,
        trafficData: TrafficData?
    )
}