package com.example.educalink_ev2.ui.screens

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.ui.components.ImagenInteligente // Asegúrate de tener este componente
import com.example.educalink_ev2.viewmodel.PerfilViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    authNavController: NavController, // Controlador para salir al Login
    viewModel: PerfilViewModel
) {
    val context = LocalContext.current
    val usuarioState by viewModel.usuarioState.collectAsState()
    val fotoUri by viewModel.fotoUri.collectAsState()

    // Lógica para tomar foto
    var uriTemporalParaCamara by remember { mutableStateOf<Uri?>(null) }

    val launcherCamara = rememberLauncherForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { fueExitosa ->
        if (fueExitosa && uriTemporalParaCamara != null) {
            viewModel.guardarFoto(uriTemporalParaCamara.toString())
        }
    }

    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { esConcedido ->
        if (esConcedido) {
            // Aquí deberías volver a intentar abrir la cámara
            // O usar tu lógica de getUriTemporal desde el ViewModel si la tienes expuesta
            Toast.makeText(context, "Permiso concedido. Presiona de nuevo.", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Permiso denegado", Toast.LENGTH_SHORT).show()
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

        // Foto de perfil
        ImagenInteligente(uri = fotoUri)

        Button(onClick = { launcherPermisos.launch(Manifest.permission.CAMERA) }) {
            Text("Actualizar Foto")
        }

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Nombre: ${usuarioState.nombre}", style = MaterialTheme.typography.bodyLarge)
                Text("Email: ${usuarioState.email}", style = MaterialTheme.typography.bodyLarge)
                Text("Carrera: ${usuarioState.carrera}", style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(Modifier.weight(1f))

        // BOTÓN CERRAR SESIÓN
        Button(
            onClick = {
                viewModel.cerrarSesion()
                // Navegamos al Login y borramos el historial para no poder volver
                authNavController.navigate(AppScreens.LoginScreen.route) {
                    popUpTo(AppScreens.MainScreen.route) { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar Sesión")
        }
    }
}