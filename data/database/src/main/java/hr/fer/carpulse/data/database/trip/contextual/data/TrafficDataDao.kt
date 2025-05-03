package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrafficDataDao: ITrafficDataDao {

    @Query("SELECT * FROM traffic_data WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<List<TrafficDataEntity>>

    @Delete
    abstract override fun delete(trafficDataEntity: TrafficDataEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override suspend fun insert(trafficDataEntity: TrafficDataEntity)
}