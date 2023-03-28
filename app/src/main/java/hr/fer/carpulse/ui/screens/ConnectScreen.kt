package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import hr.fer.carpulse.ui.components.ScreenTopBar

@Composable
fun ConnectScreen(
    navController: NavController
) {

    ScreenTopBar(
        onBackArrowClick = {
            navController.popBackStack()
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Connect Screen"
            )
        }
    }
}