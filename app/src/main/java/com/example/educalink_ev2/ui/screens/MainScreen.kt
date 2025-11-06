package com.example.educalink_ev2.ui.screens

// --- ¡IMPORT DE ANIMACIÓN AÑADIDO! ---
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
// --- ¡IMPORT DE NavController ANIMADO AÑADIDO! ---
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
// --- FIN IMPORTS DE ANIMACIÓN ---
import com.example.educalink_ev2.navigation.AppNavigation
import com.example.educalink_ev2.ui.components.BottomNavigationBar

// 1. AÑADIMOS EL "OptIn"
@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    authNavController: NavController
) {

    val appNavController = rememberAnimatedNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController = appNavController) }
    ) { innerPadding ->

        AppNavigation(
            navController = appNavController,
            authNavController = authNavController,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.padding(innerPadding)
        )
    }
}