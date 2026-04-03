package com.mikitazayanchkouski.imageskmp.core.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

val LightColorScheme = lightColorScheme(
    primary = lightThemePrimary,
    onPrimary = lightThemeOnPrimary,
    secondary = lightThemeSecondary,
    onSecondary = lightThemeOnSecondary,
    background = lightThemeBackground,
    onBackground = lightThemeOnBackground,
    surface = lightThemeSurface,
    onSurface = lightThemeOnSurface,
    onSurfaceVariant = lightThemeOnSurfaceVariant,
    error = lightThemeError
)

val DarkColorScheme = darkColorScheme(
    primary = darkThemePrimary,
    onPrimary = darkThemeOnPrimary,
    secondary = darkThemeSecondary,
    onSecondary = darkThemeOnSecondary,
    background = darkThemeBackground,
    onBackground = darkThemeOnBackground,
    surface = darkThemeSurface,
    onSurface = darkThemeOnSurface,
    onSurfaceVariant = darkThemeOnSurfaceVariant,
    error = darkThemeError
)