package com.remtrik.m3khelper.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.color.DynamicColors

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_unified_primary,
    onPrimary = md_theme_dark_onPrimary,
    onPrimaryContainer = md_theme_dark_primaryContainer
)

private val LightColorScheme = lightColorScheme(
    primary = md_theme_unified_primary,
    onPrimary = md_theme_light_onPrimary,
    onPrimaryContainer = md_theme_light_primaryContainer
)


@Composable
fun M3KHelperTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = DynamicColors.isDynamicColorAvailable(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}