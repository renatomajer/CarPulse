package hr.fer.carpulse.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import hr.fer.carpulse.data.database.driver.DriverDataDao
import hr.fer.carpulse.data.database.driver.DriverDataEntity
import hr.fer.carpulse.data.database.trip.TripSummaryDao
import hr.fer.carpulse.data.database.trip.TripSummaryEntity
import hr.fer.carpulse.data.database.trip.obd.OBDReadingEntity
import hr.fer.carpulse.data.database.trip.obd.OBDReadingsDao

@Database(
    entities = [DriverDataEntity::class, TripSummaryEntity::class, OBDReadingEntity::class],
    version = 1
)
abstract class CarPulseDatabase : RoomDatabase() {
    abstract fun driverDataDao(): DriverDataDao

    abstract fun tripSummaryDao(): TripSummaryDao

    abstract fun obdReadingsDao(): OBDReadingsDao
}
