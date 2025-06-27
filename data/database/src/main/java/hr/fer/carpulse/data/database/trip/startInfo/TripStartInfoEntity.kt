package hr.fer.carpulse.data.database.trip.startInfo

import androidx.room.Entity

@Entity(tableName = "trip_start_info", primaryKeys = ["tripUUID"])
data class TripStartInfoEntity(
    val pids01_20: String,
    val fuelType: String,
    val pids21_40: String,
    val pids41_60: String,
    val vehicle: String,
    val appVersion: String,
    val deviceName: String,
    val operator: String,
    val fingerprint: String,
    val androidId: String,
    val tripUUID: String,
    val tripStartTimestamp: String,
    val driverEmail: String
)