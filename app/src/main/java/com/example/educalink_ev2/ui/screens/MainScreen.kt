package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.educalink_ev2.navigation.AppNavigation
import com.example.educalink_ev2.ui.components.BottomNavigationBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(windowSizeClass: WindowSizeClass) { // Recibe el tamaño
    val navController = rememberNavController()

    Scaffold(
        // Pasa el NavController a tu barra inferior
        bottomBar = { BottomNavigationBar(navController = navController) }
    ) { innerPadding ->

        // Pasa el padding, el tamaño y el controller a tu NavHost
        AppNavigation(
            navController = navController,
            windowSizeClass = windowSizeClass,
            modifier = Modifier.padding(innerPadding) // Pasa el padding
        )
    }
}