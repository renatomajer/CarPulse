package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.data.api.mqtt.MQTTClient
import hr.fer.carpulse.data.api.mqtt.publish.Timestamp
import hr.fer.carpulse.data.api.mqtt.publish.TripReadingPublishData
import hr.fer.carpulse.data.api.mqtt.publish.TripStartInfoPublishData
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.TrafficData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripStartInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DataApiImpl(
    private val mqttClient: MQTTClient
) : DataApi {

    private val format = Json { encodeDefaults = true }

    override fun connectToMqttBroker() {
        mqttClient.connect()
    }

    override fun disconnectFromMqttBroker() {
        mqttClient.disconnect()
    }

    override fun sendDriverData(driverData: DriverData) {
        val jsonString = format.encodeToString(listOf(driverData))
        mqttClient.publish(topic = DRIVER_TOPIC, msg = jsonString, qos = 0, retained = false)
//        Log.d("debug_log", jsonString)
    }

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        val tripStartInfoPublishData = TripStartInfoPublishData.createPublishData(tripStartInfo)
        val jsonString = format.encodeToString(tripStartInfoPublishData)
        mqttClient.publish(topic = TRIP_TOPIC, msg = jsonString, qos = 0, retained = false)
//        Log.d("debug_log", jsonString)
    }

    override fun sendTripReadingData(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading,
        trafficData: TrafficData?
    ) {

        val obdData = OBDReading.createPublishData(obdReading)

        val tripReadingPublishData = TripReadingPublishData(
            locationData = locationData,
            trafficData = trafficData,
            sensorData = emptyList(),
            weatherData = weatherData,
            tripId = tripUUID,
            obdData = obdData,
            timestamp = Timestamp(obdData.timestamp.toString())
        )

        val jsonString = format.encodeToString(tripReadingPublishData)

        Log.d("debug_log", jsonString)

        mqttClient.publish(topic = DRIVE_DATA_TOPIC, msg = jsonString, qos = 0, retained = false)
    }

    companion object {
        private const val DRIVER_TOPIC = "Auto/Drivers"
        private const val TRIP_TOPIC = "Auto/Trips"
        private const val DRIVE_DATA_TOPIC = "Auto/OdbData"
    }
}
