package hr.fer.carpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.OrangeColor
import hr.fer.carpulse.ui.theme.smallBoldText
import hr.fer.carpulse.ui.theme.smallExtraLightText
import hr.fer.carpulse.ui.theme.smallLightText

@Composable
fun DriveComponent(
    modifier: Modifier = Modifier,
    onDriveClick: () -> Unit,
    startDate: String,
    startTime: String,
    duration: String,
    distance: String,
    isUploaded: Boolean,
    isUploading: Boolean
) {

    Column(modifier = modifier.fillMaxWidth().clickable { onDriveClick() }) {

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier
                    .height(83.dp),
                painter = painterResource(R.drawable.color_path),
                contentDescription = null,
                tint = OrangeColor
            )

            Spacer(modifier = Modifier.weight(0.3f))

            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = startDate, style = smallBoldText)
                    Spacer(modifier = Modifier.width(7.dp))
                    Spacer(
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .background(color = OrangeColor)
                            .size(11.dp)
                    )
                    Spacer(modifier = Modifier.width(7.dp))
                    Text(text = startTime, style = smallBoldText)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = stringResource(R.string.driving_history_screen_drive_duration, duration),
                    style = smallLightText
                )
                Text(
                    text = stringResource(R.string.driving_history_screen_drive_distance, distance),
                    style = smallLightText
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (isUploaded) {
                    Icon(
                        painter = painterResource(R.drawable.ic_uploaded),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.driving_history_screen_drive_uploaded),
                        style = smallExtraLightText
                    )

                } else if (isUploading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = LightGrayColor
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.driving_history_screen_uploading_drive),
                        style = smallExtraLightText
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_not_uploaded),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.driving_history_screen_drive_not_uploaded),
                        style = smallExtraLightText
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(LightGrayColor)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun DriveComponentNotUploadedPreview() {
    DriveComponent(
        onDriveClick = {},
        isUploaded = false,
        isUploading = false,
        startDate = "25.5.2025.",
        startTime = "15:30",
        duration = "15",
        distance = "15"
    )
}

@Preview(showBackground = true)
@Composable
private fun DriveComponentUploadedPreview() {
    DriveComponent(
        onDriveClick = {},
        isUploaded = true,
        isUploading = false,
        startDate = "25.5.2025.",
        startTime = "15:30",
        duration = "15",
        distance = "15"
    )
}

@Preview(showBackground = true)
@Composable
private fun DriveComponentUploadingPreview() {
    DriveComponent(
        onDriveClick = {},
        isUploaded = false,
        isUploading = true,
        startDate = "25.5.2025.",
        startTime = "15:30",
        duration = "15",
        distance = "15"
    )
}