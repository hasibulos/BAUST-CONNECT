package com.example.baustclubh.ui.theme


import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp


// Colors
val PrimaryBlue = Color(0xFF2196F3)
val BackgroundDark = Color(0xFF0F0F0F)
val CardBackground = Color(0xFF1E1E1E)
val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFFB0B0B0)

// Typography - defined only once
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 16.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 24.sp
    )
)

// Color Scheme
private val DarkColorScheme = darkColorScheme(
    primary = PrimaryBlue,
    background = BackgroundDark,
    surface = CardBackground,
    onPrimary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextGray
)

@Composable
fun BAUSTConnectTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}