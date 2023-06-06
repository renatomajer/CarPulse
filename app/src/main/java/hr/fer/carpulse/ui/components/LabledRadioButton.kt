package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import hr.fer.carpulse.ui.theme.Purple200


@Composable
fun LabeledRadioButton(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    text: String
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(
                unselectedColor = Color.LightGray,
                selectedColor = Purple200
            )
        )

        Text(text = text, color = MaterialTheme.colors.onBackground)
    }
}

@Preview
@Composable
fun Prev() {
    LabeledRadioButton(selected = true, onClick = { /*TODO*/ }, text = "Male")
}