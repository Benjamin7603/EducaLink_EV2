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

// Importa todo lo necesario
import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.ui.screens.*
import com.example.educalink_ev2.viewmodel.PerfilViewModel
import com.example.educalink_ev2.viewmodel.PerfilViewModelFactory
import com.example.educalink_ev2.viewmodel.RegistroViewModel
import com.example.educalink_ev2.viewmodel.RegistroViewModelFactory

@Composable
fun AppNavigation(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext

    // 1. Crea la instancia ÚNICA del Repositorio
    val repository = remember { UsuarioRepository(context) }

    NavHost(
        navController = navController,
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier
    ) {

        composable(AppScreens.HomeScreen.route) {
            // (Asegúrate de que este nombre coincida con tu archivo de la Guía 9)
            AdaptiveHomeScreen(
                windowSizeClass = windowSizeClass,
                navController = navController
            )
        }

        // --- ¡AQUÍ ESTÁ EL CAMBIO! ---
        composable(AppScreens.ProfileScreen.route) {

            // 2. Crea la Fábrica para PerfilViewModel
            val factory = remember { PerfilViewModelFactory(repository) }

            // 3. Inyecta el ViewModel
            val viewModel: PerfilViewModel = viewModel(factory = factory)

            ProfileScreen(navController = navController, viewModel = viewModel)
        }

        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        // --- Ruta de Registro (Ya estaba bien de la Guía 12) ---
        composable(AppScreens.RegistroScreen.route) {
            val factory = remember { RegistroViewModelFactory(repository) }
            val viewModel: RegistroViewModel = viewModel(factory = factory)
            RegistroScreen(navController = navController, viewModel = viewModel)
        }

        // --- Ruta de Resumen (Ya estaba bien de la Guía 11) ---
        composable(
            route = AppScreens.ResumenScreen.route,
            // ... (argumentos) ...
        ) { backStackEntry ->
            val nombre = backStackEntry.arguments?.getString("nombre") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            val carrera = backStackEntry.arguments?.getString("carrera") ?: ""

            ResumenScreen(nombre = nombre, email = email, carrera = carrera)
        }
    }
}