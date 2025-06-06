package hr.fer.carpulse.di

import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import hr.fer.carpulse.bluetooth.AndroidBluetoothController
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.data.repository.AssistantRepositoryImpl
import hr.fer.carpulse.data.repository.DataStoreRepositoryImpl
import hr.fer.carpulse.data.repository.DriverDataRepositoryImpl
import hr.fer.carpulse.data.repository.TripsRepositoryImpl
import hr.fer.carpulse.domain.repointerfaces.AssistantRepository
import hr.fer.carpulse.domain.repointerfaces.DataStoreRepository
import hr.fer.carpulse.domain.repointerfaces.DriverDataRepository
import hr.fer.carpulse.domain.repointerfaces.TripsRepository
import hr.fer.carpulse.domain.usecase.driver.GetDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SaveDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendDriverDataUseCase
import hr.fer.carpulse.domain.usecase.driver.SendTripReviewUseCase
import hr.fer.carpulse.domain.usecase.mqtt.ConnectToBrokerUseCase
import hr.fer.carpulse.domain.usecase.mqtt.DisconnectFromBrokerUseCase
import hr.fer.carpulse.domain.usecase.preferences.ReadLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.preferences.SaveLocalStorageStateUseCase
import hr.fer.carpulse.domain.usecase.trip.GetAllTripSummariesUseCase
import hr.fer.carpulse.domain.usecase.trip.SaveTripSummaryUseCase
import hr.fer.carpulse.domain.usecase.trip.SendTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetSavedWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.GetWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveTrafficDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SaveWeatherDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.SendTripReadingDataUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.StopLocationDataUpdateUseCase
import hr.fer.carpulse.domain.usecase.trip.contextual.data.UpdateLocationDataUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetAllOBDReadingsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetAllUnsentUUIDsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.GetOBDReadingsUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.SaveOBDReadingUseCase
import hr.fer.carpulse.domain.usecase.trip.obd.UpdateSummarySentStatusUseCase
import hr.fer.carpulse.domain.usecase.trip.review.DeleteTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.review.GetTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.review.SaveTripReviewUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.DeleteTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.GetTripStartInfoUseCase
import hr.fer.carpulse.domain.usecase.trip.startInfo.SaveTripStartInfoUseCase
import hr.fer.carpulse.util.PhoneUtils
import hr.fer.carpulse.viewmodel.ConnectDeviceViewModel
import hr.fer.carpulse.viewmodel.HomeScreenViewModel
import hr.fer.carpulse.viewmodel.OnboardingViewModel
import hr.fer.carpulse.viewmodel.SplashScreenViewModel
import hr.fer.carpulse.viewmodel.TalkWithAssistantViewModel
import hr.fer.carpulse.viewmodel.TripReviewScreenViewModel
import hr.fer.carpulse.viewmodel.TripsScreenViewModel
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
        DriverDataRepositoryImpl(
            driverDataDao = get(),
            driverDataMapper = get(),
            dataApi = get(),
            tripReviewDao = get(),
            tripReviewMapper = get()
        )
    }

    single<TripsRepository> {
        TripsRepositoryImpl(
            dataApi = get(),
            tripSummaryMapper = get(),
            tripSummaryDao = get(),
            tripStartInfoDao = get(),
            locationDataDao = get(),
            weatherDataDao = get(),
            trafficDataDao = get(),
            tripStartInfoMapper = get(),
            obdReadingMapper = get(),
            obdReadingsDao = get(),
            locationDataMapper = get(),
            weatherDataMapper = get(),
            trafficDataMapper = get(),
            servicesApi = get(),
            weatherApi = get(),
            trafficApi = get()
        )
    }

    single<DataStoreRepository> {
        DataStoreRepositoryImpl(dataStore = get())
    }

    single<AssistantRepository> {
        AssistantRepositoryImpl(assistantApi = get())
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
        GetTripReviewUseCase(driverDataRepository = get())
    }

    single {
        SaveTripReviewUseCase(driverDataRepository = get())
    }

    single {
        DeleteTripReviewUseCase(driverDataRepository = get())
    }

    single {
        SendTripStartInfoUseCase(tripsRepository = get())
    }

    single {
        GetTripStartInfoUseCase(tripsRepository = get())
    }

    single {
        SaveTripStartInfoUseCase(tripsRepository = get())
    }

    single {
        DeleteTripStartInfoUseCase(tripsRepository = get())
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
        UpdateSummarySentStatusUseCase(tripsRepository = get())
    }

    single {
        GetWeatherDataUseCase(tripsRepository = get())
    }

    single {
        GetTrafficDataUseCase(tripsRepository = get())
    }

    single {
        GetLocationDataUseCase(tripsRepository = get())
    }

    single {
        UpdateLocationDataUseCase(tripsRepository = get())
    }

    single {
        StopLocationDataUpdateUseCase(tripsRepository = get())
    }

    single {
        GetSavedLocationDataUseCase(tripsRepository = get())
    }

    single {
        SaveLocationDataUseCase(tripsRepository = get())
    }

    single {
        GetSavedWeatherDataUseCase(tripsRepository = get())
    }

    single {
        SaveWeatherDataUseCase(tripsRepository = get())
    }

    single {
        SaveTrafficDataUseCase(tripsRepository = get())
    }

    single {
        GetSavedTrafficDataUseCase(tripsRepository = get())
    }

    single {
        ConnectToBrokerUseCase(tripsRepository = get())
    }

    single {
        DisconnectFromBrokerUseCase(tripsRepository = get())
    }

    single {
        SendTripReadingDataUseCase(tripsRepository = get())
    }

    single {
        PhoneUtils(context = androidApplication())
    }


    viewModel {
        ConnectDeviceViewModel(bluetoothController = get())
    }

    viewModel {
        HomeScreenViewModel(
            bluetoothController = get(),
            phoneUtils = get(),
            getDriverDataUseCase = get(),
            sendTripStartInfoUseCase = get(),
            saveTripStartInfoUseCase = get(),
            saveLocalStorageStateUseCase = get(),
            readLocalStorageStateUseCase = get(),
            saveOBDReadingUseCase = get(),
            saveTripSummaryUseCase = get(),
            getLocationDataUseCase = get(),
            updateLocationDataUseCase = get(),
            stopLocationDataUpdateUseCase = get(),
            getWeatherDataUseCase = get(),
            saveLocationDataUseCase = get(),
            saveWeatherDataUseCase = get(),
            saveTrafficDataUseCase = get(),
            connectToBrokerUseCase = get(),
            disconnectFromBrokerUseCase = get(),
            sendTripReadingDataUseCase = get(),
            sendDriverDataUseCase = get(),
            getTrafficDataUseCase = get(),
            dataStoreRepository = get()
        )
    }

    viewModel {
        OnboardingViewModel(
            dataStoreRepository = get(),
            saveDriverDataUseCase = get(),
            getDriverDataUseCase = get(),
            sendDriverDataUseCase = get(),
            connectToBrokerUseCase = get(),
            disconnectFromBrokerUseCase = get()
        )
    }

    viewModel {
        SplashScreenViewModel(dataStoreRepository = get())
    }

    viewModel {
        TripReviewScreenViewModel(
            sendTripReviewUseCase = get(),
            getDriverDataUseCase = get(),
            readLocalStorageStateUseCase = get(),
            saveTripReviewUseCase = get(),
            connectToBrokerUseCase = get(),
            disconnectFromBrokerUseCase = get()
        )
    }

    viewModel {
        TripsScreenViewModel(
            getAllTripSummariesUseCase = get(),
            getAllUnsentUUIDsUseCase = get(),
            getOBDReadingsUseCase = get(),
            updateSummarySentStatusUseCase = get(),
            getTripStartInfoUseCase = get(),
            deleteTripStartInfoUseCase = get(),
            sendTripStartInfoUseCase = get(),
            deleteTripReviewUseCase = get(),
            getTripReviewUseCase = get(),
            sendTripReviewUseCase = get(),
            getSavedLocationDataUseCase = get(),
            getSavedWeatherDataUseCase = get(),
            getSavedTrafficDataUseCase = get(),
            connectToBrokerUseCase = get(),
            disconnectFromBrokerUseCase = get(),
            sendTripReadingDataUseCase = get(),
            getDriverDataUseCase = get(),
            sendDriverDataUseCase = get()
        )
    }

    viewModel {
        TalkWithAssistantViewModel(
            assistantRepository = get(),
            getDriverDataUseCase = get(),
            application = androidApplication()
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