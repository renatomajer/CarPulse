package hr.fer.carpulse.data.database.trip.obd

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class OBDReadingsDao : IOBDReadingsDao {

    @Query("SELECT * FROM obd_readings")
    abstract override fun getAll(): Flow<List<OBDReadingEntity>>

    @Query("SELECT * FROM obd_readings WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<List<OBDReadingEntity>>

    @Insert
    abstract override suspend fun insert(obdReadingEntity: OBDReadingEntity)

    @Delete
    abstract override fun delete(obdReadingEntity: OBDReadingEntity)
}