package com.example.educalink_ev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier

// 1. Imports necesarios para calcular el tamaño de la pantalla
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

// 2. Importa tu Tema (de ui/theme/Theme.kt)
import com.example.educalink_ev2.ui.theme.EducaLink_EV2Theme

// 3. Importa tu HomeScreen (de ui/screens/HomeScreen.kt)
import com.example.educalink_ev2.ui.screens.AdaptiveHomeScreen

class MainActivity : ComponentActivity() {

    // 4. Anotación necesaria para usar calculateWindowSizeClass
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EducaLink_EV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 5. Calcula la clase de tamaño (Móvil, Tablet, etc.)
                    val windowSizeClass = calculateWindowSizeClass(this)

                    // 6. Pasa ese tamaño a tu HomeScreen.
                    // (Tu HomeScreen debe estar esperando este parámetro)
                    AdaptiveHomeScreen(windowSizeClass = windowSizeClass)
                }
            }
        }
    }
}