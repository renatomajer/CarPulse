package hr.fer.carpulse.data.database


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import hr.fer.carpulse.data.database.driver.DriverDataDao
import hr.fer.carpulse.data.database.driver.DriverDataEntity
import hr.fer.carpulse.data.database.trip.TripSummaryDao
import hr.fer.carpulse.data.database.trip.TripSummaryEntity
import hr.fer.carpulse.data.database.trip.contextual.data.CoordinateConverters
import hr.fer.carpulse.data.database.trip.contextual.data.LocationDataDao
import hr.fer.carpulse.data.database.trip.contextual.data.LocationDataEntity
import hr.fer.carpulse.data.database.trip.contextual.data.TrafficDataDao
import hr.fer.carpulse.data.database.trip.contextual.data.TrafficDataEntity
import hr.fer.carpulse.data.database.trip.contextual.data.WeatherConverters
import hr.fer.carpulse.data.database.trip.contextual.data.WeatherDataDao
import hr.fer.carpulse.data.database.trip.contextual.data.WeatherDataEntity
import hr.fer.carpulse.data.database.trip.obd.OBDReadingEntity
import hr.fer.carpulse.data.database.trip.obd.OBDReadingsDao
import hr.fer.carpulse.data.database.trip.startInfo.TripStartInfoDao
import hr.fer.carpulse.data.database.trip.startInfo.TripStartInfoEntity

@Database(
    entities = [
        DriverDataEntity::class,
        TripSummaryEntity::class,
        OBDReadingEntity::class,
        TripStartInfoEntity::class,
        LocationDataEntity::class,
        WeatherDataEntity::class,
        TrafficDataEntity::class
    ],
    version = 3,
    exportSchema = false
)
@TypeConverters(WeatherConverters::class, CoordinateConverters::class)
abstract class CarPulseDatabase : RoomDatabase() {
    abstract fun driverDataDao(): DriverDataDao

    abstract fun tripSummaryDao(): TripSummaryDao

    abstract fun obdReadingsDao(): OBDReadingsDao

    abstract fun tripStartInfoDao(): TripStartInfoDao

    abstract fun locationDataDao(): LocationDataDao

    abstract fun weatherDataDao(): WeatherDataDao

    abstract fun trafficDataDao(): TrafficDataDao
}
