package hr.fer.carpulse.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import hr.fer.carpulse.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),

    body2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 12.sp
    ),

    h2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    h6 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    ),
    subtitle2 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.ExtraLight,
        fontSize = 14.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)


val robotoFontFamily = FontFamily(
    Font(R.font.roboto)
)

val title = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp,
    color = Color.Black
)

val subtitle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 24.sp,
    color = Color.Black
)

val thinText = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Thin,
    fontSize = 18.sp,
    color = Color.Black
)

val menuSubtitle = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Normal,
    fontSize = 16.sp,
    color = Color.Black
)

val smallLightText = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Light,
    fontSize = 16.sp,
    color = Color.Black
)

val smallThinText = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.Thin,
    fontSize = 16.sp,
    color = Color.Black
)

val gaugeLabel = TextStyle(
    fontFamily = FontFamily.SansSerif,
    fontWeight = FontWeight.ExtraLight,
    fontSize = 18.sp,
    color = Color.Black
)