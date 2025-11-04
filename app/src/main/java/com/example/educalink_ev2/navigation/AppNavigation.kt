package com.example.educalink_ev2.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

// Importa todas tus pantallas
import com.example.educalink_ev2.ui.screens.*
import com.example.educalink_ev2.viewmodel.RegistroViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier
    ) {

        composable(AppScreens.HomeScreen.route) {
            AdaptiveHomeScreen(windowSizeClass = windowSizeClass)
        }

        // 1. Pasa el NavController a ProfileScreen
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }

        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        // 2. Pasa el NavController y el ViewModel a RegistroScreen
        composable(AppScreens.RegistroScreen.route) {
            val viewModel: RegistroViewModel = viewModel()
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        // 3. Define los argumentos para ResumenScreen
        composable(
            route = AppScreens.ResumenScreen.route,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 4. Extrae los argumentos y los pasa a la pantalla
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val carrera = backStackEntry.arguments?.getString("carrera") ?: ""

            ResumenScreen(nombre = nombre, email = email, carrera = carrera)
        }
    }
}