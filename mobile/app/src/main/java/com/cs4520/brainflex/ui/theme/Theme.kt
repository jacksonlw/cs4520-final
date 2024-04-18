package com.cs4520.brainflex.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable


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