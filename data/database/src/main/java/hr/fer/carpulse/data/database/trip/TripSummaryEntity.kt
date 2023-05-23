package hr.fer.carpulse.data.database.trip

import androidx.room.Entity

@Entity(tableName = "trip_summary", primaryKeys = ["tripUUID"])
data class TripSummaryEntity(
    val tripUUID: String,
    val startTimestamp: Long,
    val endTimestamp: Long,
    val maxRPM: Int,
    val maxSpeed: Int,
    val sent: Boolean
) {
}