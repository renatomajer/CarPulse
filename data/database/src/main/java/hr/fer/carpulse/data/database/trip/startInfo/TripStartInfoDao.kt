package hr.fer.carpulse.data.database.trip.startInfo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TripStartInfoDao : ITripStartInfoDao {

    @Query("SELECT * FROM trip_start_info WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<TripStartInfoEntity>

    @Insert
    abstract override suspend fun insert(tripStartInfoEntity: TripStartInfoEntity)

    @Delete
    abstract override fun delete(tripStartInfoEntity: TripStartInfoEntity)
}