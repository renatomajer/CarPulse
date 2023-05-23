package hr.fer.carpulse.data.database.trip.obd

import kotlinx.coroutines.flow.Flow

interface IOBDReadingsDao {
    fun getAll(): Flow<List<OBDReadingEntity>>

    fun get(tripUUID: String): Flow<List<OBDReadingEntity>>

    fun delete(obdReadingEntity: OBDReadingEntity)

    suspend fun insert(obdReadingEntity: OBDReadingEntity)
}