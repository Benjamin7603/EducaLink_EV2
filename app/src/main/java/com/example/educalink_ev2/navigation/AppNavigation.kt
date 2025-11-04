package com.example.educalink_ev2.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

// Importa todas tus pantallas
import com.example.educalink_ev2.ui.screens.AdaptiveHomeScreen
import com.example.educalink_ev2.ui.screens.ProfileScreen
import com.example.educalink_ev2.ui.screens.ResourcesScreen
import com.example.educalink_ev2.ui.screens.RegistroScreen
import com.example.educalink_ev2.ui.screens.ResumenScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass, // Recibe el tamaño desde MainScreen
    modifier: Modifier = Modifier // Recibe el padding del Scaffold
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier // Aplica el padding aquí
    ) {

        composable(AppScreens.HomeScreen.route) {
            // Llama a la pantalla adaptable de la Guía 9
            AdaptiveHomeScreen(windowSizeClass = windowSizeClass)
        }
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen()
        }
        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        // Rutas vacías para la Guía 11
        composable(AppScreens.RegistroScreen.route) {
            // RegistroScreen(navController) // Se implementará en Guía 11
        }
        composable(AppScreens.ResumenScreen.route) {
            // ResumenScreen() // Se implementará en Guía 11
        }
    }
}