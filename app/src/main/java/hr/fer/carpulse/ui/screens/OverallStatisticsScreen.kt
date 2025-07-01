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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.SpeedStatisticsComponent
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.boldText
import hr.fer.carpulse.ui.theme.menuSubtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.util.carImageIds
import hr.fer.carpulse.util.getHoursMinutesFromMinutesDuration
import hr.fer.carpulse.viewmodel.OverallStatisticsViewModel
import org.koin.androidx.compose.getViewModel


@Composable
fun OverallStatisticsScreen(
    modifier: Modifier = Modifier,
    viewModel: OverallStatisticsViewModel = getViewModel(),
    onNavigateBack: () -> Unit
) {

    val driverStatistics = viewModel.driverStatistics

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

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(start = 38.dp, end = 19.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(33.dp))

            Image(
                modifier = Modifier
                    .width(228.dp),
                painter = painterResource(carImageIds[viewModel.carImageIndex]),
                contentDescription = null
            )

            if (viewModel.isSpeaking) {
                Icon(
                    painter = painterResource(R.drawable.ic_speaker),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.Black
                )
            } else if (viewModel.isFetchingAssistantResponse) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = LightGrayColor)
            } else {
                Spacer(modifier = Modifier.height(24.dp))
            }

            if (viewModel.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = LightGrayColor
                    )
                }
            } else if (driverStatistics == null) {
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.overall_statistics_screen_no_user_data_available),
                    style = menuSubtitle,
                    textAlign = TextAlign.Center
                )
            } else {
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
                        onClick = { viewModel.getAssistantAnalysis() },
                        enabled = !viewModel.isSpeaking && !viewModel.isFetchingAssistantResponse
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
                                text = "${driverStatistics.totalDistance} km",
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
                                text = getHoursMinutesFromMinutesDuration(driverStatistics.totalDuration),
                                style = boldText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(21.dp))

                SpeedStatisticsComponent(
                    backgroundColor = Color.White,
                    averageSpeed = driverStatistics.averageSpeed,
                    averageRpm = driverStatistics.averageRpm,
                    maxSpeed = driverStatistics.maxSpeed,
                    maxRpm = driverStatistics.maxRpm
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
                        text = "${driverStatistics.drivingAboveSpeedLimit}%",
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
                        text = "${driverStatistics.drivingWithinSpeedLimit}%",
                        style = boldText
                    )
                }

                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}