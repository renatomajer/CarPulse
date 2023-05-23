package hr.fer.carpulse.domain.common.trip

data class TripSummary(
    val tripUUID: String = "",
    val startTimestamp: Long = 0L,
    val endTimestamp: Long = 0L,
    val maxRPM: Int = 0,
    val maxSpeed: Int = 0,
    val sent: Boolean = false
)
