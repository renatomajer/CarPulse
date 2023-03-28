package hr.fer.carpulse.bluetooth

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import hr.fer.carpulse.domain.common.BluetoothDeviceDomain

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBluetoothDeviceDomain(): BluetoothDeviceDomain {
    return BluetoothDeviceDomain(
        name = name,
        address = address
    )
}