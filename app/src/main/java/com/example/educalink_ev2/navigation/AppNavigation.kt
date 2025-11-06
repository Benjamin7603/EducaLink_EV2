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
import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.ui.screens.*
import com.example.educalink_ev2.viewmodel.PerfilViewModel
import com.example.educalink_ev2.viewmodel.PerfilViewModelFactory


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
        startDestination = AppScreens.HomeScreen.route,
        modifier = modifier,


        enterTransition = { fadeIn(animationSpec = tween(300)) },
        exitTransition = { fadeOut(animationSpec = tween(300)) }
    ) {

        composable(AppScreens.HomeScreen.route) {
            AdaptiveHomeScreen(
                windowSizeClass = windowSizeClass,
                navController = navController,
                authNavController = authNavController
            )
        }

        composable(AppScreens.ProfileScreen.route) {
            val factory = remember { PerfilViewModelFactory(repository) }
            val viewModel: PerfilViewModel = viewModel(factory = factory)
            ProfileScreen(
                navController = navController,
                authNavController = authNavController,
                viewModel = viewModel
            )
        }

        composable(AppScreens.ResourcesScreen.route) {
            ResourcesScreen()
        }

        composable(AppScreens.EncuentraSedeScreen.route) {
            EncuentraSedeScreen()
        }
    }
}