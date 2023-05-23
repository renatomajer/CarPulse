package hr.fer.carpulse.data.database.trip.obd

import androidx.room.Entity

@Entity(tableName = "obd_readings", primaryKeys = ["tripUUID", "timestamp"])
data class OBDReadingEntity(
    val tripUUID: String,
    val rpm: String,
    val speed: String,
    val relativeThrottlePosition: String,
    val absoluteThrottlePositionB: String,
    val throttlePosition: String,
    val acceleratorPedalPositionE: String,
    val engineLoad: String,
    val acceleratorPedalPositionD: String,
    val timestamp: Long
) {
}