package hr.fer.carpulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.ui.theme.subtitle

@Composable
fun RecordingButton(
    modifier: Modifier = Modifier,
    text: String,
    imageRes: Int,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        elevation = null,
        shape = RoundedCornerShape(13.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.White,
            disabledBackgroundColor = Color.White
        ),
        enabled = enabled,
        onClick = onClick
    ) {
        Text(modifier = Modifier.padding(end = 12.dp), text = text, style = subtitle)
        Image(painter = painterResource(imageRes), contentDescription = null)
    }
}