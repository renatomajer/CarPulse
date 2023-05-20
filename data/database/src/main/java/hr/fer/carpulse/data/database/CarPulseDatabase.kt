package hr.fer.carpulse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [DriverDataEntity::class],
    version = 1
)
abstract class CarPulseDatabase : RoomDatabase() {
    abstract fun driverDataDao(): DriverDataDao
}
