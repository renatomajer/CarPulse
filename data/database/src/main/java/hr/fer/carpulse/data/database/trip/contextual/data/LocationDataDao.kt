package hr.fer.carpulse.data.database.trip.contextual.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LocationDataDao : ILocationDataDao {

    @Query("SELECT * FROM location_data WHERE tripUUID = :tripUUID")
    abstract override fun get(tripUUID: String): Flow<List<LocationDataEntity>>

    @Delete
    abstract override fun delete(locationDataEntity: LocationDataEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract override suspend fun insert(locationDataEntity: LocationDataEntity)

}