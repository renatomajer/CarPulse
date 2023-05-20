package hr.fer.carpulse.data.database.di

import androidx.room.Room
import hr.fer.carpulse.data.database.CarPulseDatabase
import hr.fer.carpulse.data.database.IDriverDataDao
import hr.fer.carpulse.data.database.mapper.DriverDataMapper
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
}