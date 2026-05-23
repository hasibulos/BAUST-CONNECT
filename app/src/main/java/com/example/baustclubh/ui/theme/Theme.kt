//package com.example.baustclubh.ui.theme
//
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Typography
//import androidx.compose.material3.darkColorScheme
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontFamily
//import androidx.compose.ui.unit.sp
//
//// --- FIGMA EXACT COLORS ---
//val PrimaryBlue = Color(0xFF00A3FF)    // উজ্জ্বল নীল (Figma accent)
//val BackgroundDark = Color(0xFF0A1929) // গাঢ় নেভি ব্লু ব্যাকগ্রাউন্ড
//val CardBackground = Color(0xFF132F4C) // কার্ডের জন্য হালকা নেভি ব্লু
//val TextWhite = Color(0xFFFFFFFF)
//val TextGray = Color(0xFFB2BAC2)       // ফিগমার হালকা ধূসর টেক্সট
//
//// Typography
//val Typography = Typography(
//    bodyLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontSize = 16.sp
//    ),
//    titleLarge = TextStyle(
//        fontFamily = FontFamily.Default,
//        fontSize = 24.sp,
//        color = TextWhite
//    )
//)
//
//// Color Scheme
//private val DarkColorScheme = darkColorScheme(
//    primary = PrimaryBlue,
//    background = BackgroundDark,
//    surface = CardBackground,
//    onPrimary = Color.Black,
//    onBackground = TextWhite,
//    onSurface = TextWhite
//)
//
//@Composable
//fun BAUSTConnectTheme(content: @Composable () -> Unit) {
//    MaterialTheme(
//        colorScheme = DarkColorScheme,
//        typography = Typography,
//        content = content
//    )
//}
package com.example.baustclubh.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp

// --- ১. রানটাইম ডাইনামিক কালারস (কন্টেইনার অবজেক্ট) ---
object ThemeColors {
    var primary = Color(0xFF00A3FF)
    var background = Color(0xFF0A1929)
    var card = Color(0xFF132F4C)
    var textMain = Color(0xFFFFFFFF)
    var textSub = Color(0xFFB2BAC2)
}

// --- ২. আপনার স্ক্রিনগুলোর পুরনো ভেরিয়েবল নাম এখানে ম্যাপ করা হলো ---
// এর ফলে আপনার অন্য কোনো ফাইলে কোড চেঞ্জ করা লাগবে না!
val PrimaryBlue: Color @Composable get() = MaterialTheme.colorScheme.primary
val BackgroundDark: Color @Composable get() = MaterialTheme.colorScheme.background
val CardBackground: Color @Composable get() = MaterialTheme.colorScheme.surface
val TextWhite: Color @Composable get() = MaterialTheme.colorScheme.onBackground
val TextGray: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

// Typography
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

// ডার্ক কালার স্কিম (আপনার ফিগমা থিম)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF00A3FF),
    background = Color(0xFF0A1929),
    surface = Color(0xFF132F4C),
    onPrimary = Color.Black,
    onBackground = Color(0xFFFFFFFF),
    onSurfaceVariant = Color(0xFFB2BAC2)
)

// লাইট কালার স্কিম (ফোন নরমাল মুডে থাকলে যা দেখাবে)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF00A3FF),       // প্রাইমারি ব্লু ঠিকই থাকবে
    background = Color(0xFFF8FAFC),    // ব্যাকগ্রাউন্ড হবে সাদা
    surface = Color(0xFFFFFFFF) ,      // কার্ড হবে পিওর হোয়াইট
    onPrimary = Color.White,
    onBackground = Color(0xFF0A1929),  // সাদা ব্যাকগ্রাউন্ডে টেক্সট হবে ডার্ক নেভি
    onSurfaceVariant = Color(0xFF64748B) // লাইট মুডের সাব-টেক্সট (ধূসর)
)

@Composable
fun BAUSTConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}