package hr.fer.carpulse.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.subtitle

@Composable
fun CarPulseEstimationComponent(
    modifier: Modifier = Modifier,
    description: String,
    selectedGrade: Int,
    onGradeClick: (Int) -> Unit
) {

    val maxGrade = 5

    Column(modifier = modifier.fillMaxWidth()) {
        Text(text = description, style = subtitle)

        Spacer(modifier = Modifier.height(6.dp))

        Row {
            for (gradeIndex: Int in 0..<maxGrade) {
                IconButton(onClick = {
                    onGradeClick(gradeIndex + 1)
                }) {
                    Icon(
                        painter = painterResource(R.drawable.ic_star_light),
                        contentDescription = null,
                        tint = if (gradeIndex <= selectedGrade - 1) {
                            Color.Black
                        } else {
                            Color.LightGray
                        }
                    )
                }
            }
        }
    }
}