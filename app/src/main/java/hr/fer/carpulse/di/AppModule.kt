package hr.fer.carpulse.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import hr.fer.carpulse.bluetooth.AndroidBluetoothController
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.data.repository.DataStoreRepositoryImpl
import hr.fer.carpulse.data.repository.DriverDataRepositoryImpl
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SaveDriverDataUseCase
import hr.fer.carpulse.viewmodel.ConnectScreenViewModel
import hr.fer.carpulse.viewmodel.HomeScreenViewModel
import hr.fer.carpulse.viewmodel.SplashScreenViewModel
import hr.fer.carpulse.viewmodel.UserDataScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<BluetoothController> {
        AndroidBluetoothController(context = androidApplication())
    }

    single<DriverDataRepository> {
        DriverDataRepositoryImpl(driverDataDao = get(), mapper = get())
    }

    single {
        GetDriverDataUseCase(driverDataRepository = get())
    }

    single {
        SaveDriverDataUseCase(driverDataRepository = get())
    }

    viewModel {
        ConnectScreenViewModel(bluetoothController = get())
    }

    viewModel {
        HomeScreenViewModel(bluetoothController = get())
    }

    viewModel {
        UserDataScreenViewModel(
            dataStoreRepository = get(),
            saveDriverDataUseCase = get(),
            getDriverDataUseCase = get()
        )
    }

    viewModel {
        SplashScreenViewModel(dataStoreRepository = get())
    }

    single<DataStoreRepository> {
        DataStoreRepositoryImpl(dataStore = get())
    }
}


// TODO: put this code inside a separate datastore module in the data folder
private const val USER_PREFERENCES = "user_preferences"
val dataStoreModule = module {

    single {
        PreferenceDataStoreFactory.create(
            corruptionHandler = ReplaceFileCorruptionHandler(
                produceNewData = { emptyPreferences() }
            ),
            migrations = listOf(SharedPreferencesMigration(androidApplication(), USER_PREFERENCES)),
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob()),
            produceFile = { androidApplication().preferencesDataStoreFile(USER_PREFERENCES) }
        )
    }
}