package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.delay

@Composable
fun AuthLoadingScreen(navController: NavController) {
    val context = LocalContext.current.applicationContext

    // Aquí no usamos 'remember' porque solo queremos chequear una vez al entrar
    val repository = UsuarioRepository(context)

    LaunchedEffect(Unit) {
        // Pequeña pausa para que se vea el logo (opcional)
        delay(500)

        // Verificamos si Firebase tiene sesión iniciada
        if (repository.isUserLoggedIn()) {
            // Si SÍ está logueado -> Vamos al Home
            navController.navigate(AppScreens.MainScreen.route) {
                popUpTo(AppScreens.AuthLoadingScreen.route) { inclusive = true }
            }
        } else {
            // Si NO está logueado -> Vamos al Login
            navController.navigate(AppScreens.LoginScreen.route) {
                popUpTo(AppScreens.AuthLoadingScreen.route) { inclusive = true }
            }
        }
    }

    // Pantalla de carga visual
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}