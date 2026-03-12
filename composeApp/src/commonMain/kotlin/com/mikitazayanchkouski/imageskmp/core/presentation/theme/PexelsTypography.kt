package com.mikitazayanchkouski.imageskmp.core.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import imageskmp.composeapp.generated.resources.Res
import imageskmp.composeapp.generated.resources.lora_bold
import imageskmp.composeapp.generated.resources.lora_bold_italic
import imageskmp.composeapp.generated.resources.lora_italic
import imageskmp.composeapp.generated.resources.lora_medium
import imageskmp.composeapp.generated.resources.lora_medium_italic
import imageskmp.composeapp.generated.resources.lora_regular
import imageskmp.composeapp.generated.resources.lora_semi_bold
import imageskmp.composeapp.generated.resources.lora_semi_bold_italic
import org.jetbrains.compose.resources.Font

val LoraFont
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.lora_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.lora_bold_italic,
            weight = FontWeight.W700,
            style = FontStyle.Italic
        ),
        Font(
            resource = Res.font.lora_italic,
            weight = FontWeight.W400,
            style = FontStyle.Italic
        ),
        Font(
            resource = Res.font.lora_medium,
            weight = FontWeight.W500,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.lora_medium_italic,
            weight = FontWeight.W500,
            style = FontStyle.Italic
        ),
        Font(
            resource = Res.font.lora_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.lora_semi_bold,
            weight = FontWeight.W600,
            style = FontStyle.Normal
        ),
        Font(
            resource = Res.font.lora_semi_bold_italic,
            weight = FontWeight.W600,
            style = FontStyle.Italic
        )
    )

val LoraTypography
    @Composable get() = Typography(
        titleLarge = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            lineHeight = 36.sp,
            letterSpacing = 1.sp
        ),
        titleMedium = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            lineHeight = 28.sp,
            letterSpacing = 1.sp
        ),
        titleSmall = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
            lineHeight = 26.sp,
            letterSpacing = 1.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.sp
        ),
        bodySmall = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.sp
        ),
        labelMedium = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 1.sp
        ),
        labelSmall = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            letterSpacing = 1.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            lineHeight = 18.sp,
            letterSpacing = 1.sp
        ),
        displaySmall = TextStyle(
            fontFamily = LoraFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 11.sp,
            lineHeight = 14.sp,
            letterSpacing = 1.sp
        )
    )