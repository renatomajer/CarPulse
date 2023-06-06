package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.navigation.Screens
import hr.fer.carpulse.ui.components.ScreenTopBar
import hr.fer.carpulse.ui.theme.*
import hr.fer.carpulse.viewmodel.SettingsScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun SettingsScreen(
    navController: NavController
) {

    val settingsScreenViewModel = getViewModel<SettingsScreenViewModel>()

    ScreenTopBar(
        onBackArrowClick = {
            navController.popBackStack()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mediumPadding, bottom = mediumPadding, start = microPadding),
                text = stringResource(id = R.string.basic_information),
                style = Typography.h6,
                color = Teal200
            )

            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(start = microPadding, end = microPadding, bottom = mediumPadding)
                .clickable {
                    navController.navigate(Screens.UserDataScreen.route)
                }) {
                Text(text = stringResource(id = R.string.driver_info), style = Typography.body1)
                Text(
                    text = stringResource(id = R.string.driver_info_description),
                    style = Typography.h6,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray
                )
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(smallSpacerHeight)
                    .background(Color.Gray)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = mediumPadding, bottom = mediumPadding, start = microPadding),
                text = stringResource(id = R.string.data_saving),
                style = Typography.h6,
                color = Teal200
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Column(modifier = Modifier
                    .weight(1f)
                    .padding(start = microPadding, end = microPadding, bottom = mediumPadding)
                    .clickable { }) {
                    Text(
                        text = stringResource(id = R.string.save_data_locally),
                        style = Typography.body1
                    )
                    Text(
                        text = stringResource(id = R.string.save_data_locally_description),
                        style = Typography.h6,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                }

                val storeLocally = settingsScreenViewModel.storeLocally.value
                Switch(
                    checked = storeLocally,
                    onCheckedChange = { settingsScreenViewModel.saveLocalStorageState(it) }
                )
            }
        }
    }

}