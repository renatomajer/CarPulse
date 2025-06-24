package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.SpeedStatisticsComponent
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.boldText
import hr.fer.carpulse.ui.theme.menuSubtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.getHoursMinutesFromMinutesDuration


@Composable
fun OverallStatisticsScreen(modifier: Modifier = Modifier, onNavigateBack: () -> Unit = {}) {

    val onAssistantClick = {}
    val distance = 250
    val durationMinutes = 132
    val averageSpeed = 25
    val averageRpm = 25
    val maxSpeed = 70
    val maxRpm = 70
    val drivingAboveSpeedLimitPercentage = 50
    val drivingWithinSpeedLimitPercentage = 60

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppBackgroundColor)
    ) {
        Spacer(modifier = Modifier.height(65.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                modifier = Modifier
                    .padding(start = 30.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100)
                    ),
                onClick = onNavigateBack
            ) {
                Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(text = stringResource(R.string.overall_statistics_screen_title), style = title)

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .padding(end = 30.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 38.dp, end = 19.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            //TODO: insert users car
            Image(
                modifier = Modifier
                    .width(228.dp),
                painter = painterResource(R.drawable.car_logo),
                contentDescription = null
            )

            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.overall_statistics_screen_listen_to_assistant),
                    style = title
                )

                IconButton(
                    modifier = Modifier.size(70.dp),
                    onClick = onAssistantClick
                ) {
                    Image(
                        modifier = Modifier.size(70.dp),
                        painter = painterResource(R.drawable.ic_ai_assistant),
                        contentDescription = null
                    )
                }
            }
            Spacer(modifier = Modifier.height(21.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
            ) {

                // Distance
                Row(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .weight(1f)
                        .padding(start = 10.dp, top = 15.dp, bottom = 15.dp, end = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(painter = painterResource(R.drawable.ic_distance), null)

                    Column {
                        Text(
                            text = stringResource(R.string.trip_details_screen_distance),
                            style = menuSubtitle
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = "$distance km",
                            style = boldText
                        )
                    }
                }

                Spacer(modifier = Modifier.width(22.dp))

                // Duration
                Row(
                    modifier = Modifier
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .weight(1f)
                        .padding(start = 10.dp, top = 15.dp, bottom = 15.dp, end = 7.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(painter = painterResource(R.drawable.ic_stopwatch), null)

                    Column {
                        Text(
                            text = stringResource(R.string.trip_details_screen_duration),
                            style = menuSubtitle
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = getHoursMinutesFromMinutesDuration(durationMinutes),
                            style = boldText
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(21.dp))

            SpeedStatisticsComponent(
                backgroundColor = Color.White,
                averageSpeed = averageSpeed,
                averageRpm = averageRpm,
                maxSpeed = maxSpeed,
                maxRpm = maxRpm
            )

            Spacer(modifier = Modifier.height(19.dp))

            // Above speed limit
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(top = 9.dp, bottom = 9.dp)
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
                    .background(color = Color.White, shape = RoundedCornerShape(15.dp))
                    .padding(top = 9.dp, bottom = 9.dp)
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

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}