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

// TODO: Standard paddings and margins should be defined for Composables

// TODO: I'm not going to expand on this for the assignment.
//  Light/dark mode is beyond scope of this project.
@Composable
fun TastyTradeWatchlistsTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}