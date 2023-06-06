package hr.fer.carpulse.data.api

import android.util.Log
import hr.fer.carpulse.data.api.mqtt.MQTTClient
import hr.fer.carpulse.data.api.mqtt.publish.*
import hr.fer.carpulse.domain.common.contextual.data.LocationData
import hr.fer.carpulse.domain.common.contextual.data.WeatherData
import hr.fer.carpulse.domain.common.driver.DriverData
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.domain.common.trip.TripReview
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
        mqttClient.publish(topic = DRIVER_TOPIC, msg = jsonString, qos = 0, retained = true)
//        Log.d("debug_log", jsonString)
    }

    override fun sendTripReview(tripReview: TripReview) {
        val tripReviewPublishData = TripReviewPublishData.createPublishData(tripReview)
        val jsonString = format.encodeToString(tripReviewPublishData)
        mqttClient.publish(topic = REVIEW_TOPIC, msg = jsonString, qos = 0, retained = true)
//        Log.d("debug_log", jsonString)
    }

    override fun sendTripStartInfo(tripStartInfo: TripStartInfo) {
        val tripStartInfoPublishData = TripStartInfoPublishData.createPublishData(tripStartInfo)
        val jsonString = format.encodeToString(tripStartInfoPublishData)
        mqttClient.publish(topic = TRIP_TOPIC, msg = jsonString, qos = 0, retained = true)
//        Log.d("debug_log", jsonString)
    }

    override fun sendTripReadingData(
        locationData: LocationData,
        weatherData: WeatherData,
        tripUUID: String,
        obdReading: OBDReading
    ) {

        val obdData = OBDReading.createPublishData(obdReading)

        val tripReadingPublishData = TripReadingPublishData(
            locationData = locationData,
            trafficData = TrafficData(),
            sensorData = emptyList(),
            weatherData = weatherData,
            tripId = tripUUID,
            obdData = obdData,
            timestamp = Timestamp(obdData.timestamp.toString())
        )

        val jsonString = format.encodeToString(tripReadingPublishData)

        Log.d("debug_log", jsonString)

        mqttClient.publish(topic = DRIVE_DATA_TOPIC, msg = jsonString, qos = 0, retained = true)
    }

    companion object {
        private const val DRIVER_TOPIC = "Auto/Drivers"
        private const val TRIP_TOPIC = "Auto/Trips"
        private const val DRIVE_DATA_TOPIC = "Auto/OdbData"
        private const val REVIEW_TOPIC = "Auto/DriversReviewTrip"
    }
}
