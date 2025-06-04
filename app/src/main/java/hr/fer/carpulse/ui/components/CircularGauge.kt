package hr.fer.carpulse.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import hr.fer.carpulse.ui.theme.LightGrayColor
import hr.fer.carpulse.ui.theme.extraLightText


@Composable
fun CircularGauge(
    value: Float,
    maxValue: Float,
    minValue: Float = 0f,
    height: Int,
    width: Int,
    indicatorColor: Color, // Orange color
    label: String,
    indicatorWidth: Float = 30f,
    backgroundArcColor: Color = LightGrayColor,
    backgroundArcWidth: Float = 30f,
    animationDuration: Int = 300
) {
    // Calculate the sweep angle based on the current value
    val sweepAngle = (180f * (value - minValue) / (maxValue - minValue)).coerceIn(0f, 180f)

    // Animate the sweep angle
    val animatedSweepAngle by animateFloatAsState(
        targetValue = sweepAngle,
        animationSpec = tween(animationDuration),
        label = "gaugeAnimation"
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(height.dp)
            .width(width.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = this.size.width
            val canvasHeight = this.size.height
            val centerX = canvasWidth / 2
            val centerY = canvasHeight - (canvasWidth / 2)
            val radius = (canvasWidth.coerceAtMost(canvasHeight) - indicatorWidth) / 2

            // Draw background arc (Light Gray)
            drawArc(
                color = backgroundArcColor,
                startAngle = 180f,
                sweepAngle = 180f,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = backgroundArcWidth, cap = StrokeCap.Butt)
            )

            // Draw indicator arc (Orange)
            drawArc(
                color = indicatorColor,
                startAngle = 180f,
                sweepAngle = animatedSweepAngle,
                useCenter = false,
                topLeft = Offset(centerX - radius, centerY - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = indicatorWidth, cap = StrokeCap.Butt)
            )
        }

        Text(
            text = "${value.toInt()} $label",
            style = extraLightText
        )
    }
}
