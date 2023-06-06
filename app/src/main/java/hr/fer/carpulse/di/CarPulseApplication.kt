package hr.fer.carpulse.di

import android.app.Application
import hr.fer.carpulse.data.api.di.apiModule
import hr.fer.carpulse.data.database.di.databaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CarPulseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@CarPulseApplication)
            modules(appModule, dataStoreModule, databaseModule, apiModule)
        }
    }
}