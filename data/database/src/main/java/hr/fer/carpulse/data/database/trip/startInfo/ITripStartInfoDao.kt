package hr.fer.carpulse.data.database.trip.startInfo

import kotlinx.coroutines.flow.Flow

interface ITripStartInfoDao {
    fun get(tripUUID: String): Flow<TripStartInfoEntity>

    suspend fun insert(tripStartInfoEntity: TripStartInfoEntity)

    fun delete(tripStartInfoEntity: TripStartInfoEntity)
}