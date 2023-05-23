package hr.fer.carpulse.data.database.di

import androidx.room.Room
import hr.fer.carpulse.data.database.CarPulseDatabase
import hr.fer.carpulse.data.database.driver.IDriverDataDao
import hr.fer.carpulse.data.database.mapper.DriverDataMapper
import hr.fer.carpulse.data.database.mapper.OBDReadingMapper
import hr.fer.carpulse.data.database.mapper.TripSummaryMapper
import hr.fer.carpulse.data.database.trip.ITripSummaryDao
import hr.fer.carpulse.data.database.trip.obd.IOBDReadingsDao
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
}