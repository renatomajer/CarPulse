package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Entity

@Entity(tableName = "location_data", primaryKeys = ["tripUUID", "timestamp"])
data class LocationDataEntity(
    val tripUUID: String,
    val longitude: Double,
    val latitude: Double,
    val altitude: Double,
    val timestamp: Long
) {
}