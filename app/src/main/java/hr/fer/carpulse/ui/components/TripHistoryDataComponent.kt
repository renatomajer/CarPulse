package hr.fer.carpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.LightBlueColor
import hr.fer.carpulse.ui.theme.LightGreenColor
import hr.fer.carpulse.ui.theme.boldText
import hr.fer.carpulse.ui.theme.menuSubtitle
import hr.fer.carpulse.util.calculateDurationInMinutes
import hr.fer.carpulse.util.getHoursMinutesSeconds
import hr.fer.carpulse.util.getMinutesOrSecondsFromMinutesDuration


@Composable
fun TripHistoryDataComponent(
    modifier: Modifier = Modifier,
    tripDistance: Double,
    startAddress: String,
    endAddress: String,
    weatherDescription: String?,
    temperature: Int?,
    startTimestamp: Long,
    endTimestamp: Long,
    idlingPercent: Double,
    idlingTime: Double,
    averageSpeed: Int,
    maxSpeed: Int,
    averageRpm: Int,
    maxRpm: Int,
    drivingAboveSpeedLimitPercentage: Int,
    drivingWithinSpeedLimitPercentage: Int,
    suddenBreaking: Int,
    suddenAcceleration: Int,
    stopAndGoSituations: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(AppBackgroundColor)
    ) {
        Text(
            text = stringResource(R.string.trip_details_screen_basic_information),
            style = boldText
        )
        Spacer(modifier = Modifier.height(6.dp))

        // Basic information content
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Distance
            Column(
                modifier = Modifier
                    .background(color = LightBlueColor, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp)
                    .fillMaxHeight()
                    .weight(0.30f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(painter = painterResource(R.drawable.ic_distance), null)
                Text(
                    text = stringResource(R.string.trip_details_screen_distance),
                    style = menuSubtitle
                )
                Text(
                    text = "$tripDistance km",
                    style = boldText
                )
            }

            Spacer(modifier = Modifier.weight(0.09f))

            Column(
                modifier = Modifier.weight(0.61f)
            ) {
                // START location
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightBlueColor, shape = RoundedCornerShape(15.dp))
                        .padding(top = 15.dp, start = 12.dp, bottom = 11.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_start_location), null)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = stringResource(R.string.trip_details_screen_start_location),
                            style = menuSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = startAddress,
                        style = boldText
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // END location
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightBlueColor, shape = RoundedCornerShape(15.dp))
                        .padding(top = 15.dp, start = 12.dp, bottom = 11.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(painter = painterResource(R.drawable.ic_end_location), null)
                        Spacer(modifier = Modifier.width(15.dp))
                        Text(
                            text = stringResource(R.string.trip_details_screen_end_location),
                            style = menuSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = endAddress,
                        style = boldText
                    )
                }
            }
        }

        // Weather
        if (weatherDescription != null && temperature != null) {
            Spacer(modifier = Modifier.height(13.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = LightBlueColor, shape = RoundedCornerShape(15.dp))
                    .padding(top = 9.dp, bottom = 6.dp)
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(painter = painterResource(R.drawable.ic_weather), null)
                Spacer(modifier = Modifier.width(13.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_weather),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = weatherDescription,
                    style = boldText
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Text(
                    text = "$temperature°C",
                    style = boldText
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // About time
        Text(
            text = stringResource(R.string.trip_details_screen_about_time),
            style = boldText
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {

            Column(modifier = Modifier.weight(0.42f)) {
                // START time
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                        .padding(12.dp)
                ) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(R.drawable.ic_start_outlined), null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.trip_details_screen_start_time),
                            style = menuSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = getHoursMinutesSeconds(startTimestamp),
                        style = boldText
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // END time
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                        .padding(12.dp)
                ) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                        Icon(painter = painterResource(R.drawable.ic_end_outlined), null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = stringResource(R.string.trip_details_screen_end_time),
                            style = menuSubtitle
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = getHoursMinutesSeconds(endTimestamp),
                        style = boldText
                    )
                }
            }

            Spacer(modifier = Modifier.width(11.dp))

            // Duration
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.26f)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(start = 5.dp, top = 17.dp, end = 5.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_stopwatch),
                    modifier = Modifier.height(48.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_duration),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "${
                        calculateDurationInMinutes(
                            startTimestamp = startTimestamp,
                            endTimestamp = endTimestamp
                        )
                    } min",
                    style = boldText,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.weight(1f))
            }

            Spacer(modifier = Modifier.width(11.dp))

            // Time idling
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.32f)
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(start = 5.dp, top = 17.dp, end = 5.dp, bottom = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_sand_clock),
                    modifier = Modifier.height(48.dp),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_time_idling),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "$idlingPercent%",
                    style = boldText
                )
                Text(
                    text = "${getMinutesOrSecondsFromMinutesDuration(idlingTime)} min",
                    style = boldText
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        // About speed
        Text(
            text = stringResource(R.string.trip_details_screen_about_speed),
            style = boldText
        )
        Spacer(modifier = Modifier.height(6.dp))

        SpeedStatisticsComponent(
            backgroundColor = LightGreenColor,
            averageSpeed = averageSpeed,
            maxSpeed = maxSpeed,
            averageRpm = averageRpm,
            maxRpm = maxRpm
        )

        Spacer(modifier = Modifier.height(13.dp))

        // Above speed limit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightGreenColor, shape = RoundedCornerShape(15.dp))
                .padding(top = 9.dp, bottom = 6.dp)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.ic_above), null)
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(R.string.trip_details_screen_driving_above_limit),
                style = menuSubtitle
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "$drivingAboveSpeedLimitPercentage%",
                style = boldText
            )
        }

        Spacer(modifier = Modifier.height(13.dp))

        // Within speed limit
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LightGreenColor, shape = RoundedCornerShape(15.dp))
                .padding(top = 9.dp, bottom = 6.dp)
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(painter = painterResource(R.drawable.ic_within), null)
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = stringResource(R.string.trip_details_screen_driving_within_limit),
                style = menuSubtitle
            )
            Spacer(modifier = Modifier.weight(0.5f))
            Text(
                text = "$drivingWithinSpeedLimitPercentage%",
                style = boldText
            )
        }

        Spacer(modifier = Modifier.height(17.dp))

        // Special events
        Text(
            text = stringResource(R.string.trip_details_screen_special_events),
            style = boldText
        )
        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            // Sudden breaking
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = LightGreenColor, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp)
                    .weight(0.30f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(painter = painterResource(R.drawable.ic_danger), null)
                Spacer(modifier = Modifier.height(19.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_sudden_breaking),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "$suddenBreaking",
                    style = boldText
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            // Sudden acceleration
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .background(color = LightGreenColor, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp)
                    .weight(0.30f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(painter = painterResource(R.drawable.ic_acceleration), null)
                Spacer(modifier = Modifier.height(19.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_sudden_acceleration),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "$suddenAcceleration",
                    style = boldText
                )
            }

            Spacer(modifier = Modifier.width(18.dp))

            // Stop-and-go situation
            Column(
                modifier = Modifier
                    .background(color = LightGreenColor, shape = RoundedCornerShape(15.dp))
                    .padding(15.dp)
                    .weight(0.30f),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Icon(painter = painterResource(R.drawable.ic_flag), null)
                Spacer(modifier = Modifier.height(19.dp))
                Text(
                    text = stringResource(R.string.trip_details_screen_stop_and_go),
                    style = menuSubtitle
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "$stopAndGoSituations",
                    style = boldText
                )
            }
        }

        Spacer(modifier = Modifier.height(30.dp))
    }
}


@Preview(heightDp = 1200)
@Composable
private fun TripHistoryDataComponentPreview() {
    TripHistoryDataComponent(
        tripDistance = 15.0,
        startAddress = "Jarun 5",
        endAddress = "Banjavčićeva 1A",
        weatherDescription = "Light rain",
        temperature = 12,
        startTimestamp = 1750795702000,
        endTimestamp = 1750894602000,
        idlingPercent = 30.0,
        idlingTime = 5.2,
        averageSpeed = 25,
        averageRpm = 25,
        maxSpeed = 70,
        maxRpm = 70,
        drivingAboveSpeedLimitPercentage = 50,
        drivingWithinSpeedLimitPercentage = 60,
        suddenBreaking = 5,
        suddenAcceleration = 4,
        stopAndGoSituations = 10
    )
}