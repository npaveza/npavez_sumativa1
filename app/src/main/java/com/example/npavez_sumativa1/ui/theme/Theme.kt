package com.example.npavez_sumativa1.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val AzulOscuro = Color(0xFF2196F3)
private val AzulClaro = Color(0xFF87CEEB)
private val Blanco = Color(0xFFFFFFFF)
private val GrisOscuro = Color(0xFF333333)

private val DarkColorScheme = darkColorScheme(
    primary = AzulOscuro,
    secondary = AzulClaro,
    tertiary = GrisOscuro
)

private val LightColorScheme = lightColorScheme(
    primary = AzulClaro,
    secondary = AzulOscuro,
    tertiary = Blanco
)

@Composable
fun NpavezSumativa1Theme(content: @Composable () -> Unit) {
    val dynamicColorScheme = when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(LocalContext.current) else dynamicLightColorScheme(LocalContext.current)
        }
        else -> {
            if (isSystemInDarkTheme()) DarkColorScheme else LightColorScheme
        }
    }
    MaterialTheme(colorScheme = dynamicColorScheme) {
        content()
    }
}