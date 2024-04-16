package com.cs4520.brainflex.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColors(
    background = DarkBlue,
    secondary = LightBlue,
    primary = White,
    onBackground = DarkerBlue
)

@Composable
fun BrainFlexTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = LightColorScheme,
        content = content
    )
}