package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.repository.UsuarioRepository

@Composable
fun AuthLoadingScreen(navController: NavController) {
    val repository = UsuarioRepository(LocalContext.current.applicationContext)
    val isLoggedIn by repository.isLoggedIn.collectAsState(initial = null)

    LaunchedEffect(isLoggedIn) {
        if (isLoggedIn == true) {
            navController.navigate(AppScreens.MainScreen.route) {
                popUpTo(AppScreens.AuthLoadingScreen.route) { inclusive = true }
            }
        } else if (isLoggedIn == false) {
            navController.navigate(AppScreens.LoginScreen.route) {
                popUpTo(AppScreens.AuthLoadingScreen.route) { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}