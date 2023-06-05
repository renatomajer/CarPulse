package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class WeatherDataDao : IWeatherDataDao {

    @Query("SELECT * FROM weather_data WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<WeatherDataEntity>

    @Insert
    abstract override suspend fun insert(weatherDataEntity: WeatherDataEntity)

    @Delete
    abstract override fun delete(weatherDataEntity: WeatherDataEntity)
}