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
import com.example.educalink_ev2.ui.theme.EducaLink_EV2Theme
import com.example.educalink_ev2.ui.screens.MainScreen // Importa MainScreen

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EducaLink_EV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Calcula el tamaño
                    val windowSizeClass = calculateWindowSizeClass(this)

                    // Llama a MainScreen (en lugar de HomeScreen)
                    MainScreen(windowSizeClass = windowSizeClass)
                }
            }
        }
    }
}