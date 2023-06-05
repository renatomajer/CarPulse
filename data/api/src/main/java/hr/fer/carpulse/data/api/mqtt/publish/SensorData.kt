package hr.fer.carpulse.data.api.mqtt.publish

@kotlinx.serialization.Serializable
data class SensorData(
    var sensorType: String? = null,
    var values: ArrayList<Double>? = null,
    var accuracy: Int = 0,
    var timestamp: Timestamp? = null
) {
}

@kotlinx.serialization.Serializable
data class Timestamp(
    var `$numberLong`: String? = null
) {
}