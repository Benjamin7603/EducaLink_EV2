package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.viewmodel.RegistroViewModel
import com.example.educalink_ev2.viewmodel.RegistroViewModelFactory



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    // 1. El ViewModel ahora debe ser inyectado por la Factory
    // (Esto se configura en AppNavigation.kt)
    viewModel: RegistroViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        // ... (Tu TopAppBar queda igual) ...
        topBar = {
            TopAppBar(title = { Text("Registro de Usuario") })
        }
    ) { innerPadding ->
        Column(
            // ... (Tus TextFields y Column quedan igual) ...
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Ingrese sus datos", style = MaterialTheme.typography.headlineSmall)

            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.carrera,
                onValueChange = { viewModel.onCarreraChange(it) },
                label = { Text("Carrera") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(16.dp))

            // 2. ¡Aquí está el cambio!
            Button(
                onClick = {
                    // 3. PRIMERO guarda los datos en DataStore
                    viewModel.guardarDatos()

                    // 4. LUEGO navega a la pantalla de resumen (igual que Guía 11)
                    navController.navigate(
                        AppScreens.ResumenScreen.createRoute(
                            nombre = uiState.nombre,
                            email = uiState.email,
                            carrera = uiState.carrera
                        )
                    )                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Resumen")
            }
        }
    }
}