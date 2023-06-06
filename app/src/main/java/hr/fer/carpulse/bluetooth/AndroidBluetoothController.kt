package hr.fer.carpulse.bluetooth

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.util.Log
import hr.fer.carpulse.domain.common.BluetoothDeviceDomain
import hr.fer.carpulse.domain.common.ConnectionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import java.io.IOException
import java.util.*


@SuppressLint("MissingPermission")
class AndroidBluetoothController(
    private val context: Context
) : BluetoothController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }

    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val connectedDeviceAddress = MutableStateFlow<String?>(null)

    private val _isConnected = MutableStateFlow(false)
    override val isConnected: StateFlow<Boolean>
        get() = _isConnected.asStateFlow()

    private val _scannedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val scannedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _scannedDevices.asStateFlow()

    private val _pairedDevices = MutableStateFlow<List<BluetoothDeviceDomain>>(emptyList())
    override val pairedDevices: StateFlow<List<BluetoothDeviceDomain>>
        get() = _pairedDevices.asStateFlow()

    private val _errors = MutableSharedFlow<String>()
    override val errors: SharedFlow<String>
        get() = _errors.asSharedFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBluetoothDeviceDomain()
            if (newDevice in devices) devices else devices + newDevice
        }
    }
    private var isFoundDeviceReceiverRegistered = false

    private val bluetoothStateReceiver = BluetoothStateReceiver { isConnected, bluetoothDevice ->

        if (bluetoothAdapter?.bondedDevices?.contains(bluetoothDevice) == true) {
            _isConnected.update { isConnected }
        }
//       else {
//            CoroutineScope(Dispatchers.IO).launch {
//                _errors.emit("Can't connect to a non-paired device.")
//            }
//        }

        // if the connected device has disconnected
        if (!isConnected && connectedDeviceAddress.value == bluetoothDevice.address) {
            _isConnected.update { isConnected }
            connectedDeviceAddress.update { null }

            Log.d("debug_log", "Disconnected from device: ${bluetoothDevice.address}")
        }
    }
    private var isBluetoothStateReceiverRegistered = false

    private var currentClientSocket = MutableStateFlow<BluetoothSocket?>(null)

    init {
        updatePairedDevices()

        // listen for every bluetooth connection state change
        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter().apply {
                addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED)
                addAction(BluetoothDevice.ACTION_ACL_CONNECTED)
                addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED)
            }
        )
        isBluetoothStateReceiverRegistered = true
    }

    override fun startDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) return

        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )
        isFoundDeviceReceiverRegistered = true

        updatePairedDevices()

        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_SCAN)) return

        bluetoothAdapter?.cancelDiscovery()

        if (isFoundDeviceReceiverRegistered) {
            context.unregisterReceiver(foundDeviceReceiver)
            isFoundDeviceReceiverRegistered = false
        }

        _scannedDevices.update { emptyList() }
    }

    override fun connectToDevice(device: BluetoothDeviceDomain): Flow<ConnectionResult> {
        return flow {
            if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) {
                throw SecurityException("No BLUETOOTH_CONNECT permission")
            }

            currentClientSocket.update {
                bluetoothAdapter?.getRemoteDevice(device.address)
                    ?.createRfcommSocketToServiceRecord(
                        UUID.fromString(SERVICE_UUID)
                    )
            }

            stopDiscovery()

            currentClientSocket.value?.let { socket ->
                try {
                    socket.connect()
                    emit(ConnectionResult.ConnectionEstablished)

                    // remember the address of the device we connected to (our OBD device)
                    connectedDeviceAddress.update { device.address }
                    Log.d("debug_log", "Connected to device: ${device.address}")

//                    val obd = OBDCommunication(socket)
//                    obd.readData()

                } catch (e: IOException) {
                    Log.d("debug_log", e.toString())
                    socket.close()
                    currentClientSocket.value = null
                    emit(ConnectionResult.Error("Connection was interrupted"))
                }
            }

            updatePairedDevices()

        }.flowOn(Dispatchers.IO)
    }

    override fun closeConnection() {
        currentClientSocket.value?.close()
        currentClientSocket.value = null
    }

    // TODO: see where this function should be called, to unregister bluetoothStateReceiver
    override fun release() {
        if (isFoundDeviceReceiverRegistered) {
            context.unregisterReceiver(foundDeviceReceiver)
            isFoundDeviceReceiverRegistered = false
        }

        if (isBluetoothStateReceiverRegistered) {
            context.unregisterReceiver(bluetoothStateReceiver)
            isBluetoothStateReceiverRegistered = false
        }
        closeConnection()
    }

    private fun updatePairedDevices() {
        if (!hasPermission(Manifest.permission.BLUETOOTH_CONNECT)) return

        bluetoothAdapter?.bondedDevices?.map {
            it.toBluetoothDeviceDomain()
        }?.also { devices ->
            _pairedDevices.update { devices }
        }
    }

    override fun getConnectedDeviceAddress() = connectedDeviceAddress.asStateFlow()

    override fun getCurrentClientSocket() = currentClientSocket.asStateFlow()

    private fun hasPermission(permission: String): Boolean {
        return context.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val SERVICE_UUID = "00001101-0000-1000-8000-00805F9B34FB"
    }
}