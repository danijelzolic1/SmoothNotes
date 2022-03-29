package se.zolda.smoothnotes.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import se.zolda.smoothnotes.R

val appFont = FontFamily(
    Font(R.font.raleway_bold, weight = FontWeight.Bold),
    Font(R.font.raleway_light, weight = FontWeight.Light),
    Font(R.font.raleway_medium, weight = FontWeight.Medium),
    Font(R.font.raleway_regular, weight = FontWeight.Normal),
    Font(R.font.raleway_thin, weight = FontWeight.ExtraLight)
)

// Set of Material typography styles to start with
val defaultFontFamily = appFont
val Typography = Typography(
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontFamily = defaultFontFamily,
        fontSize = 96.sp,
        letterSpacing = (-1.5).sp
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontFamily = defaultFontFamily,
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 48.sp,
        letterSpacing = 0.sp
    ),
    h4 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 34.sp,
        letterSpacing = 0.25.sp
    ),
    h5 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 24.sp,
        letterSpacing = 0.sp
    ),
    h6 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
        fontSize = 20.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 16.sp,
        letterSpacing = 0.15.sp
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
        fontSize = 14.sp,
        letterSpacing = 0.1.sp
    ),
    body1 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 14.sp,
        letterSpacing = 0.5.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
        fontSize = 14.sp,
        letterSpacing = 1.25.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Medium,
        fontFamily = defaultFontFamily,
        fontSize = 12.sp,
        letterSpacing = 0.4.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.Normal,
        fontFamily = defaultFontFamily,
        fontSize = 10.sp,
        letterSpacing = 1.5.sp
    )
)