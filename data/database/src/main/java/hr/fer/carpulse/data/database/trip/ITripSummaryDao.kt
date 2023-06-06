package hr.fer.carpulse.data.database.trip

import kotlinx.coroutines.flow.Flow

interface ITripSummaryDao {
    fun getAll(): Flow<List<TripSummaryEntity>>

    suspend fun insert(tripSummaryEntity: TripSummaryEntity)

    fun getAllUnsentUUIDs(): Flow<List<String>>

    fun updateSummarySentStatus(tripUUID: String, sent: Boolean)
}