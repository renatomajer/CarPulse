package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.navigation.Screens

@Composable
fun HomeScreen(
    navController: NavController
) {
    var showMenu by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = stringResource(
                                id = R.string.menu
                            )
                        )
                    }

                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.ConnectScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.connect_device))
                        }

                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.MeasurementsScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.show_measurements))
                        }

                        DropdownMenuItem(onClick = {
                            navController.navigate(Screens.SettingsScreen.route)
                        }) {
                            Text(text = stringResource(id = R.string.settings))
                        }
                    }
                }
            )
        }

    ) { contentPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "App content"
            )
        }
    }
}