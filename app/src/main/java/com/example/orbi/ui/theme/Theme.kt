package com.example.orbi.ui.theme


// Importações :
import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


// Mapeando a paleta para o modo claro :
private val LightColorScheme = lightColorScheme(


    primary = OrbiDarkBrown,
    secondary = OrbiOlive,
    tertiary = OrbiSand,
    background = OrbiCream,
    surface = OrbiWhite,
    onPrimary = OrbiWhite,
    onSecondary = OrbiWhite,
    onBackground = OrbiDarkBrown,
    onSurface = OrbiDarkBrown,
    surfaceVariant = OrbiLightOlive


)


// Provendo o tema global para todas as telas :
@Composable
fun OrbiTheme(


    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit


) {

    // Inicialmente focaremos no modo claro da paleta oficial :
    val colorScheme = LightColorScheme
    val view = LocalView.current


    if (!view.isInEditMode) {


        SideEffect {


            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true


        }


    }


    MaterialTheme(


        colorScheme = colorScheme,
        typography = Typography,
        content = content


    )

}