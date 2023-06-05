package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Entity
import androidx.room.TypeConverter
import hr.fer.carpulse.domain.common.contextual.data.Weather
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

@Entity(tableName = "weather_data", primaryKeys = ["tripUUID"])
data class WeatherDataEntity(
    val tripUUID: String,
    val visibility: Int,
    val timezone: Int,
    val grnd_level: Int, //Main
    val temp: Double,
    val temp_min: Double,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val feels_like: Double,
    val temp_max: Double,
    val all: Int, //Clouds
    val country: String, // Sys
    val sunrise: Long,
    val sunset: Long,
    val sysId: Long,
    val message: Int,
    val type: Int,
    val dt: Long,
    val lon: Double, // Coord
    val lat: Double,
    val name: String,
    val weather: Array<Weather>,
    val cod: Int,
    val id: Int,
    val base: String,
    val deg: Int, // Wind
    val speed: Double,
    val gust: Double
) {
}

class Converters {
    private val format = Json { encodeDefaults = true }

    @TypeConverter
    fun arrayToJson(value: Array<Weather>?) = format.encodeToString(value)

    @TypeConverter
    fun jsonToArray(value: String) =
        format.decodeFromString<Array<Weather>?>(value)
}