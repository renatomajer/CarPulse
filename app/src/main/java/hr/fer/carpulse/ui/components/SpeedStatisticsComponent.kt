package hr.fer.carpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.LightGreenColor
import hr.fer.carpulse.ui.theme.boldText
import hr.fer.carpulse.ui.theme.menuSubtitle


@Composable
fun SpeedStatisticsComponent(
    modifier: Modifier = Modifier,
    backgroundColor: Color,
    averageSpeed: Int,
    maxSpeed: Int,
    averageRpm: Int,
    maxRpm: Int
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = backgroundColor, shape = RoundedCornerShape(15.dp))
            .padding(start = 20.dp, top = 24.dp, bottom = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Icon(painter = painterResource(R.drawable.ic_speed), null)
        }

        // Speed
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, end = 46.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_speed),
                    style = menuSubtitle
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_average),
                    style = menuSubtitle
                )
                Text(
                    text = "$averageSpeed km/h",
                    style = boldText
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_maximum),
                    style = menuSubtitle
                )
                Text(
                    text = "$maxSpeed km/h",
                    style = boldText
                )
            }
        }

        // RPM
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp, end = 46.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_rpm),
                    style = menuSubtitle
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_average),
                    style = menuSubtitle
                )
                Text(
                    text = "$averageRpm",
                    style = boldText
                )
            }

            Column {
                Text(
                    text = stringResource(R.string.trip_details_screen_maximum),
                    style = menuSubtitle
                )
                Text(
                    text = "$maxRpm",
                    style = boldText
                )
            }
        }
    }
}


@Preview
@Composable
private fun SpeedStatisticsComponentPreview() {
    SpeedStatisticsComponent(
        averageSpeed = 25,
        averageRpm = 25,
        maxSpeed = 70,
        maxRpm = 70,
        backgroundColor = LightGreenColor
    )
}