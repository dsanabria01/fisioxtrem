package com.example.fisioxtrem.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF006A6A), // Verde clínico
    onPrimary = Color.White,
    secondary = Color(0xFF4A6363),
    tertiary = Color(0xFF0061A4),
    background = Color(0xFFFAFDFB),
    surface = Color.White,
    surfaceVariant = Color(0xFFDAE5E4) // Color para las Cards
)

@Composable
fun FisioXtremTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}