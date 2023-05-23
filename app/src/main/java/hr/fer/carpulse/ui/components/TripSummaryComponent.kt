package hr.fer.carpulse.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import hr.fer.carpulse.R
import hr.fer.carpulse.domain.common.trip.TripSummary
import hr.fer.carpulse.ui.theme.Typography
import hr.fer.carpulse.ui.theme.smallPadding
import hr.fer.carpulse.util.getDateTime

@Composable
fun TripSummaryComponent(
    modifier: Modifier = Modifier,
    tripSummary: TripSummary,
    onClick: (TripSummary) -> Unit = {}
) {
    Row(
        modifier = modifier
            .clickable { onClick(tripSummary) }
            .padding(
                start = smallPadding,
                end = smallPadding,
                top = smallPadding,
                bottom = smallPadding
            )
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            Row() {
                Text(
                    text = stringResource(id = R.string.start) + ": ",
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = getDateTime(tripSummary.startTimestamp) ?: "",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
            }

            Row() {
                Text(
                    text = stringResource(id = R.string.end) + ": ",
                    style = Typography.subtitle2,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = getDateTime(tripSummary.endTimestamp) ?: "",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
            }

            Row(
                modifier = Modifier.padding(start = smallPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.max_rpm) + ": ",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = tripSummary.maxRPM.toString(),
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
            }

            Row(
                modifier = Modifier.padding(start = smallPadding)
            ) {
                Text(
                    text = stringResource(id = R.string.max_speed) + ": ",
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
                Text(
                    text = tripSummary.maxSpeed.toString(),
                    style = Typography.subtitle2,
                    color = MaterialTheme.colors.onBackground
                )
            }
        }

        Text(
            text = if (tripSummary.sent) stringResource(id = R.string.sent) else stringResource(id = R.string.not_sent),
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onBackground,
            fontWeight = if (tripSummary.sent) FontWeight.Medium else FontWeight.ExtraLight
        )
    }
}