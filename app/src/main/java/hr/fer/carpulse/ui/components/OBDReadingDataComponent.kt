package hr.fer.carpulse.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
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

        Text(text = stringResource(id = R.string.timestamp) + ": " + if (obdReading.timestamp == 0L) OBDReading.NO_DATA else obdReading.timestamp)

        Text(text = stringResource(id = R.string.rpm) + ": " + obdReading.rpm)

        Text(text = stringResource(id = R.string.speed) + ": " + obdReading.speed)

        Text(text = stringResource(id = R.string.relative_throttle_position) + ": " + obdReading.relativeThrottlePosition)

        Text(text = stringResource(id = R.string.absolute_throttle_position) + " B: " + obdReading.absoluteThrottlePositionB)

        Text(text = stringResource(id = R.string.throttle_position) + ": " + obdReading.throttlePosition)

        Text(text = stringResource(id = R.string.accelerator_pedal_position) + " E: " + obdReading.acceleratorPedalPositionE)

        Text(text = stringResource(id = R.string.engine_load) + ": " + obdReading.engineLoad)

        Text(text = stringResource(id = R.string.accelerator_pedal_position) + " D: " + obdReading.acceleratorPedalPositionD)

    }
}