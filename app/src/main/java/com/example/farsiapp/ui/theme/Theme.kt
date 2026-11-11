package com.example.farsiapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection

private val ColorScheme = lightColorScheme(
    primary = Teal,
    onPrimary = Color.White,
    primaryContainer = TealLight,
    onPrimaryContainer = TealDark,
    secondary = Cyan,
    onSecondary = Color.White,
    tertiary = Purple,
    onTertiary = Color.White,
    background = Background,
    onBackground = Color(0xFF1C1B1F),
    surface = Color.White,
    onSurface = Color(0xFF1C1B1F),
    error = Red,
)

/**
 * App theme. The whole app is Farsi, so layout direction is forced to RTL regardless of
 * the device language.
 */
@Composable
fun FarsiAppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = ColorScheme,
            typography = AppTypography,
            content = content,
        )
    }
}
