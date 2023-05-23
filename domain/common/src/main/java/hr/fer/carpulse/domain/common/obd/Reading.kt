package hr.fer.carpulse.domain.common.obd

import kotlinx.serialization.Serializable

@Serializable
data class Reading(
    val tripId: String = "",
    val obdData: OBDReading,
    val timestamp: Long
)
