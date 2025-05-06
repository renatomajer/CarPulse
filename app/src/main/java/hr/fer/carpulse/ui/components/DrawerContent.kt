package hr.fer.carpulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.OrangeColor
import hr.fer.carpulse.ui.theme.avatarBgColors
import hr.fer.carpulse.ui.theme.menuSubtitle
import hr.fer.carpulse.ui.theme.title

@Composable
fun DrawerContent(
    modifier: Modifier = Modifier,
    driverName: String,
    vehicleType: String,
    avatarBgColorIndex: Int,
    onCloseClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onDrivingHistoryClick: () -> Unit,
    onStatisticsClick: () -> Unit,
    onTalkWithAssistantClick: () -> Unit,
    onConfigureAssistantClick: () -> Unit,
    onScanAndConnectClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = AppBackgroundColor)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        IconButton(onClick = onCloseClick) {
            Icon(painterResource(R.drawable.ic_arrow_left), contentDescription = null)
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 29.dp, end = 31.dp)
        ) {
            Text(text = stringResource(R.string.home_screen_drawer_title), style = title)

            Text(text = stringResource(R.string.home_screen_drawer_profile), style = menuSubtitle)

            Column(
                modifier = Modifier.background(
                    color = Color.White,
                    shape = RoundedCornerShape(13.dp)
                )
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.padding(
                            start = 17.dp,
                            end = 17.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(30.dp)
                                .width(32.dp),
                            painter = painterResource(R.drawable.color_path),
                            contentDescription = null,
                            tint = avatarBgColors[avatarBgColorIndex]
                        )

                        //TODO: add avatar
                    }
                    Text(text = driverName, style = menuSubtitle)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier.padding(
                            start = 17.dp,
                            end = 17.dp,
                            top = 10.dp,
                            bottom = 10.dp
                        ), contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier
                                .height(30.dp)
                                .width(32.dp),
                            painter = painterResource(R.drawable.color_path),
                            contentDescription = null,
                            tint = OrangeColor
                        )

                        Image(
                            modifier = Modifier
                                .height(24.dp)
                                .width(26.dp),
                            painter = painterResource(R.drawable.car_logo),
                            contentDescription = null
                        )
                    }
                    Text(text = vehicleType, style = menuSubtitle)

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(onClick = onEditProfileClick) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = null
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = stringResource(R.string.home_screen_drawer_my_rides), style = menuSubtitle)

            MenuComponent(
                text = stringResource(R.string.home_screen_drawer_driving_history),
                iconResource = R.drawable.ic_binoculars,
                onClick = onDrivingHistoryClick
            )

            Spacer(modifier = Modifier.height(7.dp))

            MenuComponent(
                text = stringResource(R.string.home_screen_drawer_overall_statistics),
                iconResource = R.drawable.ic_grow,
                onClick = onStatisticsClick
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(text = stringResource(R.string.home_screen_drawer_ai_assistant), style = title)

            Spacer(modifier = Modifier.height(10.dp))

            MenuComponent(
                text = stringResource(R.string.home_screen_drawer_talk),
                iconResource = R.drawable.ic_ai_assistant,
                onClick = onTalkWithAssistantClick
            )

            Spacer(modifier = Modifier.height(7.dp))

            MenuComponent(
                text = stringResource(R.string.home_screen_drawer_configure),
                iconResource = R.drawable.ic_service,
                onClick = onConfigureAssistantClick
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(text = stringResource(R.string.home_screen_drawer_obd_setup), style = title)

            Spacer(modifier = Modifier.height(10.dp))

            MenuComponent(
                text = stringResource(R.string.home_screen_drawer_scan_and_connect),
                iconResource = R.drawable.ic_scan_connect,
                onClick = onScanAndConnectClick
            )
        }
    }
}


@Preview
@Composable
private fun DrawerContentPreview() {
    DrawerContent(
        driverName = "Renato",
        vehicleType = "Audi A3",
        avatarBgColorIndex = 0,
        onScanAndConnectClick = {},
        onDrivingHistoryClick = {},
        onStatisticsClick = {},
        onEditProfileClick = {},
        onTalkWithAssistantClick = {},
        onConfigureAssistantClick = {},
        onCloseClick = {}
    )
}