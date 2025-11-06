package com.example.educalink_ev2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    nombre: String,
    email: String,
    carrera: String
) {
    // --- LÓGICA DE ANIMACIÓN ---
    var isVisible by remember { mutableStateOf(false) }

    // LaunchedEffect se ejecuta una vez cuando el Composable aparece
    LaunchedEffect(Unit) {
        isVisible = true // Activa la animación
    }
    // --- FIN LÓGICA DE ANIMACIÓN ---

    // 1. Envuelve todo en AnimatedVisibility
    AnimatedVisibility(
        visible = isVisible,
        // Define la animación de entrada
        enter = fadeIn(animationSpec = tween(1000)) +
                slideInVertically(
                    initialOffsetY = { it / 2 }, // Empieza desde la mitad de la pantalla
                    animationSpec = tween(1000)
                ),
        modifier = Modifier.fillMaxSize()
    ) {
        // El resto de tu pantalla va aquí adentro
        Scaffold(
            topBar = {
                TopAppBar(title = { Text("Resumen del Registro") })
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Resumen de datos ingresados:", style = MaterialTheme.typography.headlineSmall)
                Text("Nombre: $nombre", style = MaterialTheme.typography.bodyLarge)
                Text("Email: $email", style = MaterialTheme.typography.bodyLarge)
                Text("Carrera: $carrera", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}