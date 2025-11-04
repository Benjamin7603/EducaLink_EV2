package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens

@Composable
fun ProfileScreen(navController: NavController) { // 1. Pide el NavController
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text("Pantalla de Perfil")

            // 2. Botón para ir al registro
            Button(onClick = {
                navController.navigate(AppScreens.RegistroScreen.route)
            }) {
                Text("Registrar Usuario")
            }
        }
    }
}