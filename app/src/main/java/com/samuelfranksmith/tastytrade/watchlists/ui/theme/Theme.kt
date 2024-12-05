package com.samuelfranksmith.tastytrade.watchlists.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

object TTTextStyle {
    val ScreenTitle = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight.W800,
        brush = Brush.linearGradient(
            colors = TitleGradient
        )
    )
    val SymbolTitle = TextStyle(
        fontSize = 24.sp,
        fontWeight = FontWeight.W600,
        brush = Brush.linearGradient(
            colors = SymbolGradient
        )
    )
    val PriceTitle = TextStyle(
        fontSize = 18.sp,
        fontWeight = FontWeight.W400,
        color = PurpleGrey40
    )
    val PriceValue = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W200,
        color = Grey
    )
}
