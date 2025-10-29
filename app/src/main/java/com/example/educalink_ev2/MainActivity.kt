package com.example.educalink_ev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier

// 1. Importa el Tema
import com.example.educalink_ev2.ui.theme.EducaLink_EV2Theme

// 2. Importa la PANTALLA ADAPTABLE (la nueva)
import com.example.educalink_ev2.ui.screens.AdaptiveHomeScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class) // Necesario para la API
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EducaLink_EV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 3. Calcula el tamaño de la ventana
                    val windowSizeClass = calculateWindowSizeClass(this)

                    // 4. Pasa el tamaño a tu pantalla adaptable
                    AdaptiveHomeScreen(windowSizeClass = windowSizeClass)
                }