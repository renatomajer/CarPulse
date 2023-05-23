package hr.fer.carpulse.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.ScreenTopBar
import hr.fer.carpulse.ui.components.TripSummaryComponent
import hr.fer.carpulse.ui.theme.smallPadding
import hr.fer.carpulse.viewmodel.TripsScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun TripsScreen(
    navController: NavController,
    context: Context
) {

    val tripsScreenViewModel = getViewModel<TripsScreenViewModel>()

    val trips = tripsScreenViewModel.tripSummaries.collectAsState(initial = emptyList()).value

    val toastMessage = stringResource(id = R.string.sending_data_message)

    ScreenTopBar(
        onBackArrowClick = {
            navController.popBackStack()
        },
        actions = {
            Button(
                onClick = {
                    tripsScreenViewModel.sendAll()
                    Toast.makeText(
                        context,
                        toastMessage,
                        Toast.LENGTH_LONG
                    ).show()
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent
                )
            ) {
                Text(text = stringResource(id = R.string.send_all).uppercase())
            }
        }
    ) { paddingValues ->
        LazyColumn(modifier = Modifier.padding(paddingValues)) {
            itemsIndexed(trips.reversed()) { index, tripSummary ->

                if (index == 0) {
                    TripSummaryComponent(
                        modifier = Modifier.padding(top = smallPadding),
                        tripSummary = tripSummary
                    )
                } else {
                    Spacer(
                        modifier = Modifier
                            .padding(start = smallPadding, end = smallPadding)
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                    )

                    TripSummaryComponent(
                        tripSummary = tripSummary
                    )
                }
            }
        }
    }
}