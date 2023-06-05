package hr.fer.carpulse.data.api.mqtt.publish


@kotlinx.serialization.Serializable
data class TrafficData(
    var currentTravelTime: Int = 0,
    var frc: String? = null,
    var confidence: Double = 0.0,
    var coordinates: Coordinates? = null,
    var freeFlowSpeed: Int = 0,
    var roadClosure: Boolean = false,
    var freeFlowTravelTime: Int = 0,
    var currentSpeed: Int = 0,
) {
}

@kotlinx.serialization.Serializable
data class Coordinates(
    var coordinate: ArrayList<Coordinate>? = null
)

@kotlinx.serialization.Serializable
data class Coordinate(
    var latitude: Double = 0.0,
    var longitude: Double = 0.0
) {
}