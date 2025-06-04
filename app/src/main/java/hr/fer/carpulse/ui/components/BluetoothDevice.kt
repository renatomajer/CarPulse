package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.GreenColor
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.subtitle


@Composable
fun BluetoothDevice(
    modifier: Modifier = Modifier,
    deviceName: String,
    onConnectClick: () -> Unit,
    isConnected: Boolean,
    isConnecting: Boolean
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = deviceName, style = subtitle)

        if (isConnected) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = stringResource(R.string.connect_device_screen_device_connected),
                style = subtitle,
                color = GreenColor
            )

        } else if (isConnecting) {
            CircularProgressIndicator(
                modifier = Modifier.size(30.dp),
                color = LightGrayColor
            )

        } else {
            Button(
                elevation = null,
                shape = RoundedCornerShape(23.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.White
                ),
                contentPadding = PaddingValues(horizontal = 20.dp),
                onClick = onConnectClick
            ) {
                Text(
                    text = stringResource(R.string.connect_device_screen_connect_button),
                    style = subtitle
                )
            }
        }
    }
}


@Preview
@Composable
private fun BluetoothDeviceDisconnectedPreview() {
    BluetoothDevice(
        deviceName = "OBD II",
        onConnectClick = {},
        isConnecting = false,
        isConnected = false
    )
}

@Preview
@Composable
private fun BluetoothDeviceConnectedPreview() {
    BluetoothDevice(
        deviceName = "OBD II",
        onConnectClick = {},
        isConnecting = false,
        isConnected = true
    )
}

@Preview
@Composable
private fun BluetoothDeviceConnectingPreview() {
    BluetoothDevice(
        deviceName = "OBD II",
        onConnectClick = {},
        isConnecting = true,
        isConnected = false
    )
}