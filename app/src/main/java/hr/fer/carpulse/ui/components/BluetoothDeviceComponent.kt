package hr.fer.carpulse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.BluetoothDevice
import hr.fer.carpulse.ui.theme.Purple200
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.mediumPadding

@Composable
fun BluetoothDeviceComponent(
    onClickConnect: (BluetoothDevice) -> Unit,
    onClickDisconnect: () -> Unit,
    device: BluetoothDevice,
    isConnectedTo: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                if (!isConnectedTo) {
                    onClickConnect(device)
                } else {
                    onClickDisconnect()
                }
            }
            .padding(mediumPadding)
    ) {

        Text(
            text = device.name ?: stringResource(id = R.string.no_name),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = if (isConnectedTo) stringResource(id = R.string.connected) else device.address,
            style = Typography.body2,
            color = if (isConnectedTo) Purple200 else MaterialTheme.colors.onSurface,
            modifier = Modifier.fillMaxWidth()
        )
    }
}