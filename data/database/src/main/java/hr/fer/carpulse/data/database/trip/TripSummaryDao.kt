package hr.fer.carpulse.data.database.trip

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TripSummaryDao : ITripSummaryDao {

    @Query("SELECT * FROM trip_summary")
    abstract override fun getAll(): Flow<List<TripSummaryEntity>>

    @Query("SELECT tripUUID FROM trip_summary WHERE sent = 0")
    abstract override fun getAllUnsentUUIDs(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override suspend fun insert(tripSummaryEntity: TripSummaryEntity)

    @Query("UPDATE trip_summary SET sent = :sent WHERE tripUUID = :tripUUID")
    abstract override fun updateSummarySentStatus(tripUUID: String, sent: Boolean)
}