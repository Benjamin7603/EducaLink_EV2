package com.example.educalink_ev2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings // Icono de ejemplo
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Inicio") },
            selected = false, // Lógica de selección pendiente
            onClick = { navController.navigate(AppScreens.HomeScreen.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            selected = false,
            onClick = { navController.navigate(AppScreens.ProfileScreen.route) }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Recursos") },
            label = { Text("Recursos") },
            selected = false,
            onClick = { navController.navigate(AppScreens.ResourcesScreen.route) }
        )
    }
}