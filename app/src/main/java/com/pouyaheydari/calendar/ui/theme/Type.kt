package com.pouyaheydari.calendar.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.pouyaheydari.calendar.R

private val vazirFont = FontFamily(
    Font(R.font.vazir)
)
val Typography = Typography(
    headlineLarge =
    TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 57.sp,
        fontFamily = vazirFont,
        lineHeight = 67.sp,
        letterSpacing = 0.25.sp,
    ),
    headlineMedium =
    TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 45.sp,
        fontFamily = vazirFont,
        lineHeight = 62.sp,
        letterSpacing = 0.sp,
    ),
    headlineSmall =
    TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp,
        fontFamily = vazirFont,
        lineHeight = 44.sp,
        letterSpacing = 0.sp,
    ),
    titleLarge =
    TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        fontFamily = vazirFont,
        lineHeight = 32.sp,
        letterSpacing = 0.25.sp,
    ),
    titleMedium =
    TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
        lineHeight = 32.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    titleSmall =
    TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    labelLarge =
    TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    labelMedium =
    TextStyle(
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 16.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    bodyLarge =
    TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    bodyMedium =
    TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 30.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
    bodySmall =
    TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontFamily = vazirFont,
        letterSpacing = 0.sp,
    ),
)
