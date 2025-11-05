package com.example.educalink_ev2.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.educalink_ev2.navigation.AppScreens

@Composable
fun BottomNavigationBar(navController: NavController) {

    // 1. Observamos la ruta actual
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        // 2. INICIO
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Inicio") },
            // 3. Se selecciona si la ruta actual es "home_screen"
            selected = currentRoute == AppScreens.HomeScreen.route,
            onClick = { navController.navigate(AppScreens.HomeScreen.route) }
        )

        // 4. RECURSOS
        NavigationBarItem(
            icon = { Icon(Icons.Default.Search, contentDescription = "Recursos") },
            label = { Text("Recursos") },
            // 5. Se selecciona si la ruta actual es "resources_screen"
            selected = currentRoute == AppScreens.ResourcesScreen.route,
            onClick = { navController.navigate(AppScreens.ResourcesScreen.route) }
        )

        // 6. PERFIL
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") },
            // 7. Se selecciona si la ruta actual es "profile_screen"
            selected = currentRoute == AppScreens.ProfileScreen.route,
            onClick = { navController.navigate(AppScreens.ProfileScreen.route) }
        )
    }
}
