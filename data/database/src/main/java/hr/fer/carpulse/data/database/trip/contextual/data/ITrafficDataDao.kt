package hr.fer.carpulse.data.database.trip.contextual.data

import kotlinx.coroutines.flow.Flow

interface ITrafficDataDao {
    fun get(tripUUID: String): Flow<List<TrafficDataEntity>>

    fun delete(trafficDataEntity: TrafficDataEntity)

    suspend fun insert(trafficDataEntity: TrafficDataEntity)
}