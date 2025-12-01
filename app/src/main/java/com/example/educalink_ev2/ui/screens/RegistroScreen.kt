package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

            Text("Ingrese sus datos", style = MaterialTheme.typography.titleMedium)

            // CAMPO NOMBRE
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onNombreChange(it) },
                label = { Text("Nombre Completo") },
                isError = uiState.errorNombre != null,
                supportingText = { uiState.errorNombre?.let { error -> Text(error) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO EMAIL (Ahora mostrará error si Firebase dice que ya existe)
            OutlinedTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChange(it) },
                label = { Text("Correo Electrónico") },
                isError = uiState.errorEmail != null,
                supportingText = { uiState.errorEmail?.let { error -> Text(error) } },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO CARRERA (Selector o Texto)
            // Nota: Aquí podrías usar un DropdownMenu con viewModel.carrerasDisponibles
            // Por ahora mantengo tu TextField original o un ReadOnly si prefieres el Dropdown.
            // Para simplificar y mantener tu código original, usamos un OutlinedTextField editable o de lista.
            // Aquí simularé un campo de texto simple que valida que no esté vacío,
            // pero idealmente usarías un ExposedDropdownMenuBox.
            OutlinedTextField(
                value = uiState.carrera,
                onValueChange = { viewModel.onCarreraChange(it) },
                label = { Text("Carrera") },
                isError = uiState.errorCarrera != null,
                supportingText = { uiState.errorCarrera?.let { error -> Text(error) } },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO CONTRASEÑA
            OutlinedTextField(
                value = uiState.contrasena,
                onValueChange = { viewModel.onContrasenaChange(it) },
                label = { Text("Contraseña (Min 6 caracteres)") },
                isError = uiState.errorContrasena != null,
                supportingText = { uiState.errorContrasena?.let { error -> Text(error) } },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO REPETIR CONTRASEÑA
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

            Spacer(Modifier.height(16.dp))

            // --- BOTÓN MODIFICADO PARA FIREBASE ---
            Button(
                onClick = {
                    // LLAMADA ASÍNCRONA:
                    // Le pasamos la navegación como una función (lambda) que se ejecutará
                    // SOLO si el ViewModel confirma que Firebase creó el usuario correctamente.
                    viewModel.guardarRegistro(onSuccess = {
                        navController.navigate(AppScreens.LoginScreen.route) {
                            popUpTo(AppScreens.AuthLoadingScreen.route) {
                                this.inclusive = true
                            }
                        }
                    })
                },
                enabled = uiState.esValido,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Cuenta")
            }
        }
    }
}