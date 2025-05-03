package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Entity
import androidx.room.TypeConverter
import hr.fer.carpulse.domain.common.contextual.data.Coordinate
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Entity(tableName = "traffic_data", primaryKeys = ["tripUUID", "timestamp"])
data class TrafficDataEntity (
    val tripUUID: String,
    val timestamp: Long,
    val frc: String,
    val currentSpeed: Int,
    val freeFlowSpeed: Int,
    val currentTravelTime: Int,
    val freeFlowTravelTime: Int,
    val confidence: Double,
    val roadClosure: Boolean,
    val coordinates: List<Coordinate>
)

class CoordinateConverters {
    private val format = Json { encodeDefaults = true }

    @TypeConverter
    fun listToJson(value: List<Coordinate>?) = format.encodeToString(value)

    @TypeConverter
    fun jsonToList(value: String) =
        format.decodeFromString<List<Coordinate>?>(value)
}