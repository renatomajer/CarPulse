package hr.fer.carpulse.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomDrawer
import androidx.compose.material.BottomDrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.rememberBottomDrawerState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.components.DriveComponent
import hr.fer.carpulse.ui.theme.AppBackgroundColor
import hr.fer.carpulse.ui.theme.menuSubtitle
import hr.fer.carpulse.ui.theme.title
import hr.fer.carpulse.viewmodel.TripsScreenViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DrivingHistoryScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    navigateToTripDetails: (tripUUID: String) -> Unit,
    viewModel: TripsScreenViewModel = getViewModel()
) {

    val drawerState = rememberBottomDrawerState(
        initialValue = BottomDrawerValue.Closed,
        confirmStateChange = { if (it == BottomDrawerValue.Expanded) false else true })

    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    val trips = viewModel.tripSummaries.collectAsState(initial = emptyList()).value

    val toastMessage = stringResource(id = R.string.sending_data_message)

    BottomDrawer(
        drawerState = drawerState,
        drawerShape = RoundedCornerShape(topStartPercent = 8, topEndPercent = 8),
        drawerContent = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 50.dp)
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .clickable(
                            onClick = {
                                viewModel.sendAll()
                                Toast.makeText(
                                    context,
                                    toastMessage,
                                    Toast.LENGTH_LONG
                                ).show()
                            },
                            indication = rememberRipple(),
                            interactionSource = remember { MutableInteractionSource() }
                        )
                        .padding(start = 17.dp, end = 12.dp, top = 10.dp, bottom = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.driving_history_screen_upload_all_drives),
                        style = menuSubtitle
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Image(
                        painter = painterResource(R.drawable.ic_upload),
                        contentDescription = null
                    )
                }
            }
        }
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = AppBackgroundColor)
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

                Text(text = stringResource(R.string.driving_history_screen_title), style = title)

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    modifier = Modifier.padding(end = 15.dp),
                    onClick = { coroutineScope.launch { drawerState.open() } }
                ) {
                    Icon(Icons.Filled.MoreVert, contentDescription = null)
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 38.dp, end = 26.dp, top = 34.dp)
            ) {

                trips.forEach { trip ->
                    DriveComponent(
                        onDriveClick = {
                            navigateToTripDetails(trip.tripUUID)
                        },

                    )
                }

                Spacer(modifier = Modifier.height(23.dp))
            }
        }
    }
}