package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDataDao : ILocationDataDao {

    @Query("SELECT * FROM location_data WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<List<LocationDataEntity>>

    @Delete
    abstract override fun delete(locationDataEntity: LocationDataEntity)

    @Insert
    abstract override suspend fun insert(locationDataEntity: LocationDataEntity)

}