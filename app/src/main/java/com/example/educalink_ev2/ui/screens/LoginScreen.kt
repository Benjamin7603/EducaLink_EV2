package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.viewmodel.LoginViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel
) {
    // Estados locales para el formulario
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    // Observamos el estado del login (Loading, Success, Error)
    val loginState by viewModel.loginState.collectAsState()

    // --- EFECTO DE NAVEGACIÓN ---
    // Si el estado cambia a Success, navegamos automáticamente
    LaunchedEffect(loginState) {
        if (loginState is LoginViewModel.LoginState.Success) {
            navController.navigate(AppScreens.MainScreen.route) {
                // ESTO ES CLAVE: Borramos el historial para que no pueda volver atrás al login
                popUpTo(AppScreens.AuthLoadingScreen.route) { inclusive = true }
            }
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Iniciar Sesión") }) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Scroll por si la pantalla es chica
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Bienvenido a EducaLink",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(32.dp))

            // Campo Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo Electrónico") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Campo Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Mostrar contraseña")
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            // Mostrar Error si existe
            if (loginState is LoginViewModel.LoginState.Error) {
                Text(
                    text = (loginState as LoginViewModel.LoginState.Error).mensaje,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // Botón de Ingreso (o Círculo de Carga)
            if (loginState is LoginViewModel.LoginState.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = { viewModel.iniciarLogin(email, password) },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = email.isNotBlank() && password.isNotBlank()
                ) {
                    Text("Ingresar")
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botón para ir al Registro
            TextButton(onClick = {
                navController.navigate(AppScreens.RegistroScreen.route)
            }) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}