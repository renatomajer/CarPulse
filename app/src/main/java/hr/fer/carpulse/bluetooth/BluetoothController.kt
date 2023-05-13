package hr.fer.carpulse.bluetooth

import android.bluetooth.BluetoothSocket
import hr.fer.carpulse.domain.common.BluetoothDevice
import hr.fer.carpulse.domain.common.ConnectionResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface BluetoothController {
    val isConnected: StateFlow<Boolean>
    val scannedDevices: StateFlow<List<BluetoothDevice>>
    val pairedDevices: StateFlow<List<BluetoothDevice>>
    val errors: SharedFlow<String>

    fun startDiscovery()
    fun stopDiscovery()

    fun connectToDevice(device: BluetoothDevice): Flow<ConnectionResult>
    fun closeConnection()

    fun release()

    fun getConnectedDeviceAddress(): StateFlow<String?>
    fun getCurrentClientSocket(): StateFlow<BluetoothSocket?>
}