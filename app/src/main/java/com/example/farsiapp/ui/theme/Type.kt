package com.example.farsiapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.example.farsiapp.R

val Vazirmatn = FontFamily(
    Font(R.font.vazirmatn_regular, FontWeight.Normal),
    Font(R.font.vazirmatn_medium, FontWeight.Medium),
    Font(R.font.vazirmatn_bold, FontWeight.Bold),
    Font(R.font.vazirmatn_black, FontWeight.Black),
)

private val Default = Typography()

val AppTypography = Typography(
    displayLarge = Default.displayLarge.copy(fontFamily = Vazirmatn),
    displayMedium = Default.displayMedium.copy(fontFamily = Vazirmatn),
    displaySmall = Default.displaySmall.copy(fontFamily = Vazirmatn),
    headlineLarge = Default.headlineLarge.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Black),
    headlineMedium = Default.headlineMedium.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Bold),
    headlineSmall = Default.headlineSmall.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Bold),
    titleLarge = Default.titleLarge.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Bold),
    titleMedium = Default.titleMedium.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Bold),
    titleSmall = Default.titleSmall.copy(fontFamily = Vazirmatn),
    bodyLarge = Default.bodyLarge.copy(fontFamily = Vazirmatn),
    bodyMedium = Default.bodyMedium.copy(fontFamily = Vazirmatn),
    bodySmall = Default.bodySmall.copy(fontFamily = Vazirmatn),
    labelLarge = Default.labelLarge.copy(fontFamily = Vazirmatn, fontWeight = FontWeight.Bold),
    labelMedium = Default.labelMedium.copy(fontFamily = Vazirmatn),
    labelSmall = Default.labelSmall.copy(fontFamily = Vazirmatn),
)
