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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegistroViewModel = viewModel() // 1. Obtiene el ViewModel
) {
    // 2. Observa el estado (uiState) del ViewModel
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Usuario") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Ingrese sus datos", style = MaterialTheme.typography.headlineSmall)

            // 3. Campo de texto para el Nombre
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) }, // Actualiza el VM
                label = { Text("Nombre Completo") },
                modifier = Modifier.fillMaxWidth()
            )

            // 4. Campo de texto para el Email
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) }, // Actualiza el VM
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            // 5. Campo de texto para la Carrera
            OutlinedTextField(
                value = uiState.carrera,
                onValueChange = { viewModel.onCarreraChange(it) }, // Actualiza el VM
                label = { Text("Carrera") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // 6. Botón para navegar al Resumen
            Button(
                onClick = {
                    // Pasa los datos a la pantalla de resumen usando la ruta
                    navController.navigate(
                        AppScreens.ResumenScreen.createRoute(
                            nombre = uiState.nombre,
                            email = uiState.email,
                            carrera = uiState.carrera
                        )
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ir a Resumen")
            }
        }
    }
}