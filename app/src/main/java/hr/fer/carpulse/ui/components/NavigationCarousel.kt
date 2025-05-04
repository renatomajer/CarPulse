package hr.fer.carpulse.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NavigationCarousel(
    modifier: Modifier = Modifier,
    carouselCount: Int,
    currentCarouselIndex: Int,
    onNextButtonClick: () -> Unit,
    onPreviousButtonClick: () -> Unit
) {

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            modifier = Modifier
                .alpha(if (currentCarouselIndex == 0) 0f else 100f)
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100)
                ),
            onClick = {
                if(currentCarouselIndex > 0) {
                    onPreviousButtonClick()
                }
            }
        ) {
            Icon(Icons.Outlined.KeyboardArrowLeft, contentDescription = null)
        }

        Spacer(modifier = Modifier.width(56.dp))

        for (carouselIndex: Int in 0..<carouselCount) {

            Box(
                modifier = Modifier
                    .size(19.dp)
                    .background(
                        color = if (carouselIndex == currentCarouselIndex)
                            Color.Black
                        else
                            Color.LightGray,
                        shape = RoundedCornerShape(100)
                    )
            ) { }

            if (carouselIndex < carouselCount - 1) {
                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        Spacer(modifier = Modifier.width(56.dp))

        IconButton(
            modifier = Modifier.background(
                color = Color.White,
                shape = RoundedCornerShape(100)
            ),
            onClick = onNextButtonClick
        ) {
            Icon(Icons.Outlined.KeyboardArrowRight, contentDescription = null)
        }
    }
}