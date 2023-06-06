package hr.fer.carpulse.data.database.driver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
abstract class DriverDataDao : IDriverDataDao {

    @Query("SELECT * FROM driver_data")
    abstract override fun get(): Flow<DriverDataEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract override fun insert(driverData: DriverDataEntity)
}