package com.example.educalink_ev2.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educalink_ev2.viewmodel.LocationViewModel
import com.example.educalink_ev2.viewmodel.LocationViewModelFactory
import com.google.android.gms.location.LocationServices

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuentraSedeScreen() {
    val context = LocalContext.current

    // ViewModel (aunque ya no usamos distancia, mantenemos permisos)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val factory = LocationViewModelFactory(context, fusedLocationClient)
    val viewModel: LocationViewModel = viewModel(factory = factory)

    // Coordenadas fijas de la sede
    val sedeLat = -41.470323
    val sedeLon = -72.925823

    // Permisos
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasPermission = true
            viewModel.startLocationUpdates() // se mantiene aunque ya no calcule distancia
        } else {
            hasPermission = false
        }
    }

    // Solicita permiso al iniciar
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // Detener actualizaciones al salir
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopLocationUpdates()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Encuentra tu Sede") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (!hasPermission) {
                Text(
                    "Permiso de GPS denegado.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(onClick = { permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }) {
                    Text("Reintentar Permiso")
                }

            } else {

                Icon(
                    imageVector = Icons.Default.Place,
                    contentDescription = "Sede",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(16.dp))

                // Nombre de la sede
                Text(
                    text = "Duoc UC: Sede Puerto Montt",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(8.dp))

                // Dirección visible (ya sin metros)
                Text(
                    text = "Dirección: Egaña 651, Puerto Montt",
                    style = MaterialTheme.typography.titleMedium,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(40.dp))

                // Botón para dirigir en Google Maps
                Button(
                    onClick = {
                        val gmmIntentUri = Uri.parse("google.navigation:q=$sedeLat,$sedeLon")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp)
                ) {
                    Text("Llévame a la Sede (Google Maps)", fontSize = 16.sp)
                }
            }
        }
    }
}
