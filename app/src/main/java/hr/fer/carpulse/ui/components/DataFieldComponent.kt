package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.smallLightText
import hr.fer.carpulse.ui.theme.smallThinText

@Composable
fun DataFieldComponent(modifier: Modifier = Modifier, dataTypeDescription: String, data: String?) {
    Row(modifier = modifier.fillMaxWidth()) {
        Text(text = "$dataTypeDescription: ", style = smallLightText)
        Text(
            text = data ?: stringResource(R.string.home_screen_data_no_data_label),
            style = smallThinText
        )
    }
}