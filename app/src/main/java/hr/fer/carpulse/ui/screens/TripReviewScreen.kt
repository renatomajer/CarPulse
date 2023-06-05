package hr.fer.carpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.trip.StartStopSystem
import hr.fer.carpulse.ui.components.DataTextField
import hr.fer.carpulse.ui.components.LabeledRadioButton
import hr.fer.carpulse.ui.theme.*
import hr.fer.carpulse.util.defaultKeyboardActions
import hr.fer.carpulse.viewmodel.TripReviewScreenViewModel
import org.koin.androidx.compose.getViewModel

@Composable
fun TripReviewScreen(
    navController: NavController,
    tripUUID: String?
) {

    val focusManager = LocalFocusManager.current
    val keyboardActions = defaultKeyboardActions(focusManager)

    val tripReviewScreenViewModel = getViewModel<TripReviewScreenViewModel>()

    val tripReview by tripReviewScreenViewModel.tripReview.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(start = smallPadding, end = smallPadding)
    ) {

        item {
            Text(
                text = stringResource(id = R.string.trip_review),
                style = Typography.h2,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = bigPadding, bottom = bigPadding),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onBackground
            )
        }

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = microPadding),
                text = stringResource(id = R.string.start_stop_system) + ":",
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = microPadding),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LabeledRadioButton(
                    selected = tripReview.startStopSystem == StartStopSystem.Used.value,
                    onClick = {
                        tripReviewScreenViewModel.updateStartStopSystem(used = true)
                    },
                    text = stringResource(id = R.string.used)
                )

                LabeledRadioButton(
                    selected = tripReview.startStopSystem == StartStopSystem.NotUsed.value,
                    onClick = {
                        tripReviewScreenViewModel.updateStartStopSystem(used = false)
                    },
                    text = stringResource(id = R.string.not_used)
                )
            }
        }

        item {
            DataTextField(
                value = if (tripReview.suddenAcceleration >= 0) tripReview.suddenAcceleration.toString() else "",
                placeholder = stringResource(id = R.string.sudden_acceleration),
                onChange = {
                    tripReviewScreenViewModel.updateSuddenAcceleration(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = if (tripReview.suddenDecelaration >= 0) tripReview.suddenDecelaration.toString() else "",
                placeholder = stringResource(id = R.string.sudden_deceleration),
                onChange = {
                    tripReviewScreenViewModel.updateSuddenDeceleration(it)
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = microPadding),
                text = stringResource(id = R.string.efficiency_knowledge) + ":",
                color = MaterialTheme.colors.onBackground,
                textAlign = TextAlign.Start
            )
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = microPadding),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LabeledRadioButton(
                    selected = tripReview.efficiencyKnowledge,
                    onClick = {
                        tripReviewScreenViewModel.updateEfficiencyKnowledge(efficiencyKnowledge = true)
                    },
                    text = stringResource(id = R.string.yes)
                )

                LabeledRadioButton(
                    selected = !tripReview.efficiencyKnowledge,
                    onClick = {
                        tripReviewScreenViewModel.updateEfficiencyKnowledge(efficiencyKnowledge = false)
                    },
                    text = stringResource(id = R.string.no)
                )
            }
        }

        item {
            DataTextField(
                value = if (tripReview.efficiencyEstimation >= 0) tripReview.efficiencyEstimation.toString() else "",
                placeholder = stringResource(id = R.string.efficiency_estimation),
                onChange = {
                    tripReviewScreenViewModel.updateEfficiencyEstimation(it)
                },
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
                keyboardActions = keyboardActions
            )
        }

        item {
            DataTextField(
                value = tripReview.comment,
                placeholder = stringResource(id = R.string.comment),
                onChange = {
                    tripReviewScreenViewModel.updateComment(it)
                },
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                keyboardActions = keyboardActions
            )
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier
                        .width(reviewDoneButtonSize)
                        .padding(top = mediumPadding, bottom = doubleBigPadding),
                    onClick = {
                        tripReviewScreenViewModel.sendTripReview(tripUUID = tripUUID)

                        navController.popBackStack()

                    }
                ) {
                    Text(
                        modifier = Modifier.fillMaxHeight(),
                        text = stringResource(id = R.string.done),
                    )
                }
            }
        }
    }
}