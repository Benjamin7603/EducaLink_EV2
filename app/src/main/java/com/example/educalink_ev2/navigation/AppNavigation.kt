package com.example.educalink_ev2.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

// --- IMPORTS EXPLÍCITOS (PARA QUE NO FALLE NUNCA) ---
import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.viewmodel.PerfilViewModel
import com.example.educalink_ev2.viewmodel.PerfilViewModelFactory
import com.example.educalink_ev2.ui.screens.AdaptiveHomeScreen
import com.example.educalink_ev2.ui.screens.ProfileScreen
import com.example.educalink_ev2.ui.screens.ResourcesScreen
import com.example.educalink_ev2.ui.screens.EncuentraSedeScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavigation(
    navController: NavHostController,
    authNavController: NavController,
    windowSizeClass: WindowSizeClass,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current.applicationContext
    val repository = remember { UsuarioRepository(context) }

    AnimatedNavHost(
        navController = navController,
        startDestination = AppScreens.MainScreen.route,
        modifier = modifier,
        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {

        // HOME
        composable(AppScreens.MainScreen.route) {
            AdaptiveHomeScreen(
                windowSizeClass = windowSizeClass,
                navController = navController,
                authNavController = authNavController
            )
        }

        // PERFIL
        composable(AppScreens.ProfileScreen.route) {
            val factory = remember { PerfilViewModelFactory(repository) }
            val viewModel: PerfilViewModel = viewModel(factory = factory)
            ProfileScreen(
                navController = navController,
                authNavController = authNavController,
                viewModel = viewModel
            )
        }

        // RECURSOS
        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        // GPS
        composable(AppScreens.EncuentraSedeScreen.route) {
            EncuentraSedeScreen()
        }
    }
}