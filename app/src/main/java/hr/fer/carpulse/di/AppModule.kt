package hr.fer.carpulse.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import hr.fer.carpulse.bluetooth.AndroidBluetoothController
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.data.api.Api
import hr.fer.carpulse.data.api.MockedApi
import hr.fer.carpulse.data.repository.DataStoreRepositoryImpl
import hr.fer.carpulse.data.repository.DriverDataRepositoryImpl
import hr.fer.carpulse.data.repository.TripsRepositoryImpl
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SaveDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.preferences.SaveLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.trip.GetAllTripSummariesUseCase
import hr.fer.carpulse.domain.usecase.trip.SaveTripSummaryUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.*
import hr.fer.carpulse.util.PhoneUtils
import hr.fer.carpulse.viewmodel.*
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

    // TODO put api creation in its own module
    single<Api> {
        MockedApi()
    }

    single<DriverDataRepository> {
        DriverDataRepositoryImpl(driverDataDao = get(), mapper = get(), api = get())
    }

    single<TripsRepository> {
        TripsRepositoryImpl(
            api = get(),
            tripSummaryMapper = get(),
            tripSummaryDao = get(),
            obdReadingMapper = get(),
            obdReadingsDao = get()
        )
    }

    single<DataStoreRepository> {
        DataStoreRepositoryImpl(dataStore = get())
    }

    single {
        GetDriverDataUseCase(driverDataRepository = get())
    }

    single {
        SaveDriverDataUseCase(driverDataRepository = get())
    }

    single {
        SendDriverDataUseCase(driverDataRepository = get())
    }

    single {
        SendTripReviewUseCase(driverDataRepository = get())
    }

    single {
        SendTripStartInfoUseCase(tripsRepository = get())
    }

    single {
        ReadLocalStorageStateUseCase(dataStoreRepository = get())
    }

    single {
        SaveLocalStorageStateUseCase(dataStoreRepository = get())
    }

    single {
        SaveTripSummaryUseCase(tripsRepository = get())
    }

    single {
        GetAllTripSummariesUseCase(tripsRepository = get())
    }

    single {
        GetAllOBDReadingsUseCase(tripsRepository = get())
    }

    single {
        GetOBDReadingsUseCase(tripsRepository = get())
    }

    single {
        GetAllUnsentUUIDsUseCase(tripsRepository = get())
    }

    single {
        SaveOBDReadingUseCase(tripsRepository = get())
    }

    single {
        SendOBDReadingUseCase(tripsRepository = get())
    }

    single {
        UpdateSummarySentStatusUseCase(tripsRepository = get())
    }

    single {
        PhoneUtils(context = androidApplication())
    }


    viewModel {
        ConnectScreenViewModel(bluetoothController = get())
    }

    viewModel {
        HomeScreenViewModel(
            bluetoothController = get(),
            phoneUtils = get(),
            getDriverDataUseCase = get(),
            sendTripStartInfoUseCase = get(),
            readLocalStorageStateUseCase = get(),
            saveOBDReadingUseCase = get(),
            saveTripSummaryUseCase = get(),
            sendOBDReadingUseCase = get()
        )
    }

    viewModel {
        UserDataScreenViewModel(
            dataStoreRepository = get(),
            saveDriverDataUseCase = get(),
            getDriverDataUseCase = get(),
            sendDriverDataUseCase = get()
        )
    }

    viewModel {
        SplashScreenViewModel(dataStoreRepository = get())
    }

    viewModel {
        TripReviewScreenViewModel(sendTripReviewUseCase = get(), getDriverDataUseCase = get())
    }

    viewModel {
        SettingsScreenViewModel(
            saveLocalStorageStateUseCase = get(),
            readLocalStorageStateUseCase = get()
        )
    }

    viewModel {
        TripsScreenViewModel(
            getAllTripSummariesUseCase = get(),
            getAllUnsentUUIDsUseCase = get(),
            getOBDReadingsUseCase = get(),
            sendOBDReadingUseCase = get(),
            updateSummarySentStatusUseCase = get()
        )
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