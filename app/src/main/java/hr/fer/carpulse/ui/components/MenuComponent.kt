package hr.fer.carpulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.R
import hr.fer.carpulse.ui.theme.menuSubtitle

@Composable
fun MenuComponent(
    modifier: Modifier = Modifier,
    text: String,
    iconResource: Int,
    onClick: () -> Unit
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White, shape = RoundedCornerShape(13.dp))
            .clickable(
                onClick = onClick,
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() }
            )
            .padding(start = 17.dp, end = 12.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = text, style = menuSubtitle)

        Spacer(modifier = Modifier.weight(1f))

        Image(painter = painterResource(iconResource), contentDescription = null)
    }
}


@Preview
@Composable
private fun MenuComponentPreview() {
    MenuComponent(
        text = "Driving history",
        iconResource = R.drawable.ic_edit,
        onClick = {}
    )
}