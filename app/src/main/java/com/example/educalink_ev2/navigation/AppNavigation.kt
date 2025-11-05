package com.example.educalink_ev2.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument


import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.ui.screens.*
import com.example.educalink_ev2.viewmodel.RegistroViewModel
import com.example.educalink_ev2.viewmodel.RegistroViewModelFactory

@Composable
fun AppNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current.applicationContext

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier
    ) {

        // ... (Rutas de Home, Profile, Resources quedan igual) ...
        composable(AppScreens.HomeScreen.route) {
            AdaptiveHomeScreen(windowSizeClass = windowSizeClass)
        }
        composable(AppScreens.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        // --- ¡AQUÍ ESTÁ EL CAMBIO! ---
        composable(AppScreens.RegistroScreen.route) {

            // 1. Crea una instancia del Repositorio (recordada)
            val repository = remember { UsuarioRepository(context) }

            // 2. Crea una instancia de la Fábrica (recordada)
            val factory = remember { RegistroViewModelFactory(repository) }

            // 3. Pasa la fábrica al ViewModel
            val viewModel: RegistroViewModel = viewModel(factory = factory)

            // 4. Pasa el ViewModel a la pantalla
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        // ... (La ruta de ResumenScreen queda igual que en la Guía 11) ...
        composable(
            route = AppScreens.ResumenScreen.route,
            arguments = listOf(
                navArgument("nombre") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType },
                navArgument("carrera") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val carrera = backStackEntry.arguments?.getString("carrera") ?: ""

            ResumenScreen(nombre = nombre, email = email, carrera = carrera)
        }
    }
}