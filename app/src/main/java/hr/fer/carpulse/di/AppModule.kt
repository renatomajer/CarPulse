package hr.fer.carpulse.di

import hr.fer.carpulse.bluetooth.AndroidBluetoothController
import hr.fer.carpulse.bluetooth.BluetoothController
import hr.fer.carpulse.viewmodel.ConnectScreenViewModel
import hr.fer.carpulse.viewmodel.HomeScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<BluetoothController> {
        AndroidBluetoothController(context = androidApplication())
    }

    viewModel {
        ConnectScreenViewModel(bluetoothController = get())
    }

    viewModel {
        HomeScreenViewModel(bluetoothController = get())
    }

}