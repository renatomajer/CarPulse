package hr.fer.carpulse.data.database.trip.contextual.data

import kotlinx.coroutines.flow.Flow

interface IWeatherDataDao {
    fun get(tripUUID: String): Flow<WeatherDataEntity>

    suspend fun insert(weatherDataEntity: WeatherDataEntity)

    fun delete(weatherDataEntity: WeatherDataEntity)
}