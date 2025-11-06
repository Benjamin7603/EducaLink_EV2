package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.viewmodel.RegistroViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: RegistroViewModel
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear Cuenta") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Ingrese sus datos", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))

            // --- INICIO DE CAMPOS CON ARREGLO ---
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.errorNombre != null,
                // Arreglo para "Smart cast"
                supportingText = {
                    uiState.errorNombre?.let { error -> Text(error) }
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Email") },
                isError = uiState.errorEmail != null,
                supportingText = {
                    uiState.errorEmail?.let { error -> Text(error) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.carrera,
                onValueChange = { viewModel.onCarreraChange(it) },
                label = { Text("Carrera") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { viewModel.onContrasenaChange(it) },
                label = { Text("Contraseña") },
                isError = uiState.errorContrasena != null,
                supportingText = {
                    uiState.errorContrasena?.let { error -> Text(error) }
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = uiState.repetirContrasena,
                onValueChange = { viewModel.onRepetirContrasenaChange(it) },
                label = { Text("Repetir Contraseña") },
                isError = uiState.errorRepetirContrasena != null,
                supportingText = {
                    uiState.errorRepetirContrasena?.let { error -> Text(error) }
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            // --- FIN DE CAMPOS CON ARREGLO ---

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.guardarRegistro()
                    navController.navigate(AppScreens.LoginScreen.route) {
                        // Arreglo para "inclusive"
                        popUpTo(AppScreens.AuthLoadingScreen.route) {
                            this.inclusive = true
                        }
                    }
                },
                enabled = uiState.esValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Cuenta")
            }
        }
    }
}