package com.mikitazayanchkouski.imageskmp.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val LightColorScheme = lightColorScheme(
    primary = lightThemePrimary,
    onPrimary = lightThemeOnPrimary,
    secondary = lightThemeSecondary,
    onSecondary = lightThemeOnSecondary,
    tertiary = lightThemeTertiary,
    background = lightThemeBackground,
    onBackground = lightThemeOnBackground,
    surface = lightThemeSurface,
    onSurface = lightThemeOnSurface,
    onSurfaceVariant = lightThemeOnSurfaceVariant
//    surfaceVariant = lightThemeSurfaceVariant,
//    onSurfaceVariant = lightThemeOnSurfaceVariant
)

val DarkColorScheme = darkColorScheme(
    primary = darkThemePrimary,
    onPrimary = darkThemeOnPrimary,
    secondary = darkThemeSecondary,
    background = darkThemeBackground,
    onBackground = darkThemeOnBackground,
    surface = darkThemeSurface,
    onSurface = darkThemeOnSurface,
    onSurfaceVariant = darkThemeOnSurfaceVariant,
    tertiary = darkThemeTertiary
//    surfaceVariant = darkThemeSurfaceVariant,
//    onSurfaceVariant = darkThemeOnSurfaceVariant
)