package com.irisvpn.android.appConfig.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val customFontFamily = FontFamily.SansSerif

val ToolBarTitle = TextStyle(
    fontFamily = customFontFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Medium,
    fontSize = 22.sp,
    lineHeight = 0.sp,
    letterSpacing = 0.sp
)
val TitleLarge = TextStyle(
    fontStyle = FontStyle.Normal,
    fontFamily = customFontFamily,
    fontWeight = FontWeight.SemiBold,
    fontSize = 40.sp,
    lineHeight = 0.sp,
    letterSpacing = 1.sp,
)
val TitleMedium = TextStyle(
    fontFamily = customFontFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Normal,
    fontSize = 20.sp,
    lineHeight = 17.sp,
    letterSpacing = 0.sp
)
val MediumText = TextStyle(
    fontFamily = customFontFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Medium,
    fontSize = 17.sp,
    lineHeight = 17.sp,
    letterSpacing = 0.2.sp
)
val SmallText = TextStyle(
    fontFamily = customFontFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Normal,
    fontSize = 13.sp,
    lineHeight = 17.sp,
    letterSpacing = 0.2.sp
)
val VerySmallText = TextStyle(
    fontFamily = customFontFamily,
    fontStyle = FontStyle.Normal,
    fontWeight = FontWeight.Thin,
    fontSize = 11.sp,
    lineHeight = 17.sp,
    letterSpacing = 0.sp
)