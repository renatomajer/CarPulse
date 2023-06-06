package hr.fer.carpulse.data.database.trip.contextual.data

import kotlinx.coroutines.flow.Flow

interface ILocationDataDao {
    fun get(tripUUID: String): Flow<List<LocationDataEntity>>

    fun delete(locationDataEntity: LocationDataEntity)

    suspend fun insert(locationDataEntity: LocationDataEntity)
}