package hr.fer.carpulse.data.api.mqtt.publish

import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.obd.OBDReading

@kotlinx.serialization.Serializable
data class TripReadingPublishData(
    val locationData: LocationData,
    val trafficData: TrafficData?,
    val sensorData: List<SensorData?>?,
    val weatherData: WeatherData,
    val tripId: String,
    val obdData: OBDReading,
    val timestamp: Timestamp
) {
}