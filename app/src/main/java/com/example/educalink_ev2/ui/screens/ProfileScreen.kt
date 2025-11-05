package com.example.educalink_ev2.ui.screens

import android.Manifest
import android.content.Context
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

// Importa el ViewModel (¡asegúrate de que esté inyectado!)
// (Lo inyectaremos en AppNavigation.kt)

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: PerfilViewModel // 1. Recibe el ViewModel inyectado
) {
    val context = LocalContext.current

    // 2. Observa los estados del ViewModel
    val usuarioState by viewModel.usuarioState.collectAsState()
    val fotoUri by viewModel.fotoUri.collectAsState()

    // 3. Launcher para la Cámara (devuelve True si la foto se tomó)
    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // El VM ya guardó la URI, solo notificamos (o podríamos refrescar)
            // viewModel.onFotoTomada(viewModel.fotoUri.value!!) 
        }
    }

    // 4. Launcher para Permisos (devuelve True si se otorgó)
    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso otorgado, lanzar la cámara
            val uri = viewModel.getUriTemporal(context)
            launcherCamara.launch(uri)
        } else {
            // Permiso denegado
            // (Aquí podrías mostrar un Snackbar o mensaje)
        }
    }

    // --- UI de la Pantalla de Perfil ---
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineLarge)

        // 5. Muestra la imagen (o ícono)
        ImagenInteligente(uri = fotoUri)

        // 6. Botón para tomar foto
        Button(onClick = {
            // Pide permiso antes de lanzar la cámara
            launcherPermisos.launch(Manifest.permission.CAMERA)
        }) {
            Text("Tomar Foto de Perfil")
        }

        Spacer(Modifier.height(16.dp))

        // 7. Muestra los datos de DataStore (Guía 12)
        Text("Datos del Usuario:", style = MaterialTheme.typography.headlineSmall)
        Text("Nombre: ${usuarioState.nombre}", style = MaterialTheme.typography.bodyLarge)
        Text("Email: ${usuarioState.email}", style = MaterialTheme.typography.bodyLarge)
        Text("Carrera: ${usuarioState.carrera}", style = MaterialTheme.typography.bodyLarge)

        // 8. Botón para ir al registro (Guía 11)
        Button(onClick = {
            navController.navigate(AppScreens.RegistroScreen.route)
        }) {
            Text("Editar Registro")
        }
    }
}