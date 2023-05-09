package hr.fer.carpulse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.BluetoothDevice
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.smallPadding

@Composable
fun BluetoothDeviceList(
    pairedDevices: List<BluetoothDevice>,
    scannedDevices: List<BluetoothDevice>,
    isScanning: Boolean,
    onClick: (BluetoothDevice) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(modifier = modifier) {
        item {
            Text(
                text = stringResource(id = R.string.paired_devices),
                style = Typography.h2,
                modifier = Modifier.padding(smallPadding)
            )
        }

        items(pairedDevices) { device ->
            Text(
                text = device.name ?: stringResource(id = R.string.no_name),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(smallPadding)
            )
        }

        item {
            Text(
                text = stringResource(id = R.string.scanned_devices),
                style = Typography.h2,
                modifier = Modifier.padding(smallPadding)
            )
        }

        if (isScanning) {
            item {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
        }

        items(scannedDevices) { device ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onClick(device) }
                    .padding(smallPadding)
            ) {

                Text(
                    text = device.name ?: stringResource(id = R.string.no_name),
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = device.address,
                    style = Typography.body2,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}