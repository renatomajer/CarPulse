package hr.fer.carpulse.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.obd.OBDReading
import hr.fer.carpulse.ui.theme.readingsBoxInnerPadding

@Composable
fun OBDReadingDataComponent(
    obdReading: OBDReading
) {

    Column(
        modifier = Modifier
            .border(1.dp, Color.Gray)
            .padding(readingsBoxInnerPadding)
    ) {

        Row() {
            Text(text = stringResource(id = R.string.timestamp) + ": ")
            Text(
                text = if (obdReading.timestamp == 0L) OBDReading.NO_DATA else obdReading.timestamp.toString(),
                fontWeight = FontWeight.ExtraLight
            )
        }

        Row() {
            Text(text = stringResource(id = R.string.rpm) + ": ")
            Text(text = obdReading.rpm, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.speed) + ": ")
            Text(text = obdReading.speed, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.relative_throttle_position) + ": ")
            Text(text = obdReading.relativeThrottlePosition, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.absolute_throttle_position) + " B: ")
            Text(text = obdReading.absoluteThrottlePositionB, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.throttle_position) + ": ")
            Text(text = obdReading.throttlePosition, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.accelerator_pedal_position) + " E: ")
            Text(text = obdReading.acceleratorPedalPositionE, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.engine_load) + ": ")
            Text(text = obdReading.engineLoad, fontWeight = FontWeight.ExtraLight)
        }

        Row() {
            Text(text = stringResource(id = R.string.accelerator_pedal_position) + " D: ")
            Text(text = obdReading.acceleratorPedalPositionD, fontWeight = FontWeight.ExtraLight)
        }

    }
}