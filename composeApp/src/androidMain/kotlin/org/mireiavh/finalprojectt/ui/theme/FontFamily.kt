package org.mireiavh.finalprojectt.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.mireiavh.finalprojectt.R

private val DarkBrown = Color(0xFF3E2723)
private val Crimson = Color(0xFFDC143C)
private val LightBrown = Color(0xFFD7CCC8)

val CardoFont = FontFamily(
    Font(R.font.cardo_regular, FontWeight.Normal),
    Font(R.font.cardo_bold, FontWeight.Bold)
)

val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = CardoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 30.sp
    ),
    h2 = TextStyle(
        fontFamily = CardoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    body1 = TextStyle(
        fontFamily = CardoFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = CardoFont,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)

private val LightColors = lightColors(
    primary = DarkBrown,
    primaryVariant = Crimson,
    secondary = LightBrown,
    background = LightBrown,
    surface = LightBrown,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColors = darkColors(
    primary = DarkBrown,
    primaryVariant = Crimson,
    secondary = LightBrown,
    background = Color.Black,
    surface = Color.DarkGray,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colors = colors,
        typography = AppTypography,
        shapes = Shapes(),
        content = content
    )
}
