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
import java.math.RoundingMode

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EncuentraSedeScreen() {
    val context = LocalContext.current

    // 1. Prepara el ViewModel (sigue funcionando igual por detrás)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val factory = LocationViewModelFactory(context, fusedLocationClient)
    val viewModel: LocationViewModel = viewModel(factory = factory)

    val distancia by viewModel.distancia.collectAsState()

    // Formatea la distancia
    val distanciaFormateada = remember(distancia) {
        if (distancia > 0) {
            distancia.toBigDecimal().setScale(1, RoundingMode.HALF_UP).toString()
        } else {
            "..." // Muestra "..." mientras se calcula
        }
    }

    // Coordenadas fijas de la sede
    val sedeLat = -41.4695
    val sedeLon = -72.9455

    // 2. Prepara el lanzador de permisos
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            hasPermission = true
            viewModel.startLocationUpdates()
        } else {
            hasPermission = false
        }
    }

    // 3. Pide permiso al entrar
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    // 4. Detiene actualizaciones al salir
    DisposableEffect(Unit) {
        onDispose {
            viewModel.stopLocationUpdates()
        }
    }

    // --- Interfaz de Usuario (Sin Lat/Lon) ---
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
            verticalArrangement = Arrangement.Center // Centramos todo
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

                Text(
                    text = "Duoc UC: Sede Puerto Montt",
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(32.dp))

                // Muestra la distancia calculada (¡esto te gustó!)
                Text("Estás a:", style = MaterialTheme.typography.titleLarge)

                if (distanciaFormateada == "...") {
                    CircularProgressIndicator(modifier = Modifier.padding(12.dp))
                }

                Text(
                    text = "$distanciaFormateada metros",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(Modifier.height(32.dp))

                // Botón de "Abrir en Google Maps"
                Button(
                    onClick = {
                        // 5. Lógica para abrir Google Maps
                        val gmmIntentUri = Uri.parse("google.navigation:q=$sedeLat,$sedeLon")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        // Inicia la actividad (abrir Maps)
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