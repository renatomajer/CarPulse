package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.GreenColor
import hr.fer.carpulse.ui.theme.RedColor
import hr.fer.carpulse.ui.theme.subtitle


@Composable
fun ScanningButton(modifier: Modifier = Modifier, onClick: () -> Unit, isScanning: Boolean) {

    val buttonText = if (isScanning) {
        stringResource(R.string.connect_device_screen_scanning_button_stop)
    } else {
        stringResource(R.string.connect_device_screen_scanning_button_start)
    }

    Button(
        modifier = modifier,
        elevation = null,
        shape = RoundedCornerShape(23.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = if (isScanning) RedColor else GreenColor
        ),
        contentPadding = PaddingValues(horizontal = 20.dp),
        onClick = onClick
    ) {
        Text(text = buttonText, style = subtitle)
    }
}

@Preview
@Composable
private fun ScanningButtonScanningPreview() {
    ScanningButton(onClick = {}, isScanning = true)
}

@Preview
@Composable
private fun ScanningButtonNotScanningPreview() {
    ScanningButton(onClick = {}, isScanning = false)
}