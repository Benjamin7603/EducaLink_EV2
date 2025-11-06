package com.example.educalink_ev2.ui.screens

import android.Manifest
import android.content.Context
import android.net.Uri
import android.widget.Toast // --- ¡IMPORT FALTANTE AÑADIDO AQUÍ! ---
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.ui.components.ImagenInteligente
import com.example.educalink_ev2.viewmodel.PerfilViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavController,
    authNavController: NavController,
    viewModel: PerfilViewModel
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val usuarioState by viewModel.usuarioState.collectAsState()
    val fotoUri by viewModel.fotoUri.collectAsState()

    var uriTemporalParaCamara by remember { mutableStateOf<Uri?>(null) }

    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            uriTemporalParaCamara?.let {
                viewModel.onFotoTomada(it)
            }
        } else {
            // Esta línea ahora funcionará
            Toast.makeText(context, "Captura cancelada", Toast.LENGTH_SHORT).show()
        }
    }

    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            val uri = viewModel.getUriTemporal(context)
            uriTemporalParaCamara = uri
            launcherCamara.launch(uri)
        } else {
            // Esta línea ahora funcionará
            Toast.makeText(context, "Permiso de cámara denegado", Toast.LENGTH_LONG).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge)

        ImagenInteligente(uri = fotoUri)

        Button(onClick = {
            launcherPermisos.launch(Manifest.permission.CAMERA)
        }) {
            Text("Tomar Foto de Perfil")
        }

        Spacer(Modifier.height(16.dp))

        Text("Datos del Usuario:", style = MaterialTheme.typography.headlineSmall)
        Text("Nombre: ${usuarioState.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ${usuarioState.email}", style = MaterialTheme.typography.bodyLarge)
        Text("Carrera: ${usuarioState.carrera}", style = MaterialTheme.typography.bodyLarge)

        Button(onClick = {
            authNavController.navigate(AppScreens.RegistroScreen.route)
        }) {
            Text("Registrar Nueva Cuenta")
        }

        Button(
            onClick = {
                scope.launch {
                    viewModel.cerrarSesion()
                    authNavController.navigate(AppScreens.AuthLoadingScreen.route) {
                        popUpTo(AppScreens.MainScreen.route) { inclusive = true }
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Cerrar Sesión")
        }
    }
}