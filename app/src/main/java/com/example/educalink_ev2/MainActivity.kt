package com.example.educalink_ev2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// --- ¡IMPORTS DE ANIMACIÓN AÑADIDOS! ---
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
// --- FIN IMPORTS DE ANIMACIÓN ---
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
// --- ¡IMPORT DE NavHost ANIMADO AÑADIDO! ---
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
// --- FIN IMPORT DE NavHost ANIMADO ---
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.ui.screens.AuthLoadingScreen
import com.example.educalink_ev2.ui.screens.LoginScreen
import com.example.educalink_ev2.ui.screens.MainScreen
import com.example.educalink_ev2.ui.screens.RegistroScreen
import com.example.educalink_ev2.ui.theme.EducaLink_EV2Theme
import com.example.educalink_ev2.viewmodel.LoginViewModel
import com.example.educalink_ev2.viewmodel.LoginViewModelFactory
import com.example.educalink_ev2.viewmodel.RegistroViewModel
import com.example.educalink_ev2.viewmodel.RegistroViewModelFactory

class MainActivity : ComponentActivity() {

    // 1. AÑADIMOS EL "OptIn" PARA LAS APIS DE ANIMACIÓN
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            EducaLink_EV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 2. USAMOS UN NavController ANIMADO
                    val authNavController = rememberAnimatedNavController()
                    val windowSizeClass = calculateWindowSizeClass(this)
                    val context = LocalContext.current.applicationContext

                    val repository = UsuarioRepository(context)
                    val registroFactory = RegistroViewModelFactory(repository)
                    val loginFactory = LoginViewModelFactory(repository)

                    // 3. REEMPLAZAMOS NavHost POR AnimatedNavHost
                    AnimatedNavHost(
                        navController = authNavController,
                        startDestination = AppScreens.AuthLoadingScreen.route,

                        // Definimos las animaciones por defecto para todas las pantallas
                        enterTransition = { fadeIn(animationSpec = tween(300)) },
                        exitTransition = { fadeOut(animationSpec = tween(300)) }
                    ) {

                        composable(AppScreens.AuthLoadingScreen.route) {
                            AuthLoadingScreen(navController = authNavController)
                        }

                        composable(AppScreens.LoginScreen.route) {
                            val loginViewModel: LoginViewModel = viewModel<LoginViewModel>(factory = loginFactory)
                            LoginScreen(
                                navController = authNavController,
                                viewModel = loginViewModel
                            )
                        }

                        composable(AppScreens.RegistroScreen.route) {
                            val registroViewModel: RegistroViewModel = viewModel<RegistroViewModel>(factory = registroFactory)
                            RegistroScreen(
                                navController = authNavController,
                                viewModel = registroViewModel
                            )
                        }

                        composable(AppScreens.MainScreen.route) {
                            MainScreen(
                                windowSizeClass = windowSizeClass,
                                authNavController = authNavController
                            )
                        }
                    }
                }
            }
        }
    }
}