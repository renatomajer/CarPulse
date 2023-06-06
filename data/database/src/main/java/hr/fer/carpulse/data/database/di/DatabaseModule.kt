package hr.fer.carpulse.data.database.di

import androidx.room.Room
import hr.fer.carpulse.data.database.CarPulseDatabase
import hr.fer.carpulse.data.database.driver.IDriverDataDao
import hr.fer.carpulse.data.database.mapper.DriverDataMapper
import hr.fer.carpulse.data.database.mapper.OBDReadingMapper
import hr.fer.carpulse.data.database.mapper.contextual.data.LocationDataMapper
import hr.fer.carpulse.data.database.mapper.contextual.data.WeatherDataMapper
import hr.fer.carpulse.data.database.mapper.trip.TripReviewMapper
import hr.fer.carpulse.data.database.mapper.trip.TripStartInfoMapper
import hr.fer.carpulse.data.database.mapper.trip.TripSummaryMapper
import hr.fer.carpulse.data.database.trip.ITripSummaryDao
import hr.fer.carpulse.data.database.trip.contextual.data.ILocationDataDao
import hr.fer.carpulse.data.database.trip.contextual.data.IWeatherDataDao
import hr.fer.carpulse.data.database.trip.obd.IOBDReadingsDao
import hr.fer.carpulse.data.database.trip.review.ITripReviewDao
import hr.fer.carpulse.data.database.trip.startInfo.ITripStartInfoDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {

    single<CarPulseDatabase> {
        Room.databaseBuilder(
            androidApplication(),
            CarPulseDatabase::class.java, "carpulse_database"
        ).build()
    }

    single<IDriverDataDao> {
        val database: CarPulseDatabase = get()
        database.driverDataDao()
    }

    single {
        DriverDataMapper()
    }

    single<ITripSummaryDao> {
        val database: CarPulseDatabase = get()
        database.tripSummaryDao()
    }

    single {
        TripSummaryMapper()
    }

    single<IOBDReadingsDao> {
        val database: CarPulseDatabase = get()
        database.obdReadingsDao()
    }

    single {
        OBDReadingMapper()
    }

    single<ITripStartInfoDao> {
        val database: CarPulseDatabase = get()
        database.tripStartInfoDao()
    }

    single {
        TripStartInfoMapper()
    }

    single<ITripReviewDao> {
        val database: CarPulseDatabase = get()
        database.tripReviewDao()
    }

    single {
        TripReviewMapper()
    }

    single<ILocationDataDao> {
        val database: CarPulseDatabase = get()
        database.locationDataDao()
    }

    single {
        LocationDataMapper()
    }

    single<IWeatherDataDao> {
        val database: CarPulseDatabase = get()
        database.weatherDataDao()
    }

    single {
        WeatherDataMapper()
    }
}