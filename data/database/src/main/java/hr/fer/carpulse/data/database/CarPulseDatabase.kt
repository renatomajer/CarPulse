package hr.fer.carpulse.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.fer.carpulse.data.database.driver.DriverDataDao
import hr.fer.carpulse.data.database.driver.DriverDataEntity
import hr.fer.carpulse.data.database.trip.TripSummaryDao
import hr.fer.carpulse.data.database.trip.TripSummaryEntity
import hr.fer.carpulse.data.database.trip.contextual.data.*
import hr.fer.carpulse.data.database.trip.obd.OBDReadingEntity
import hr.fer.carpulse.data.database.trip.obd.OBDReadingsDao
import hr.fer.carpulse.data.database.trip.review.TripReviewDao
import hr.fer.carpulse.data.database.trip.review.TripReviewEntity
import hr.fer.carpulse.data.database.trip.startInfo.TripStartInfoDao
import hr.fer.carpulse.data.database.trip.startInfo.TripStartInfoEntity

@Database(
    entities = [DriverDataEntity::class, TripSummaryEntity::class, OBDReadingEntity::class,
        TripStartInfoEntity::class, TripReviewEntity::class, LocationDataEntity::class, WeatherDataEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class CarPulseDatabase : RoomDatabase() {
    abstract fun driverDataDao(): DriverDataDao

    abstract fun tripSummaryDao(): TripSummaryDao

    abstract fun obdReadingsDao(): OBDReadingsDao

    abstract fun tripStartInfoDao(): TripStartInfoDao

    abstract fun tripReviewDao(): TripReviewDao

    abstract fun locationDataDao(): LocationDataDao

    abstract fun weatherDataDao(): WeatherDataDao
}
