package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen( // 1. Recibe los datos como parámetros
    nombre: String,
    email: String,
    carrera: String
) {
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

            // 2. Muestra los datos recibidos
            Text("Nombre: $nombre", style = MaterialTheme.typography.bodyLarge)
            Text("Email: $email", style = MaterialTheme.typography.bodyLarge)
            Text("Carrera: $carrera", style = MaterialTheme.typography.bodyLarge)
        }
    }
}