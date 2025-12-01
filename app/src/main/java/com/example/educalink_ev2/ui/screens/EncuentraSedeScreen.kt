package com.example.educalink_ev2.ui.screens

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educalink_ev2.viewmodel.SedeViewModel
import com.example.educalink_ev2.viewmodel.SedeViewModelFactory

@Composable
fun EncuentraSedeScreen() {
    val context = LocalContext.current

    // Inyección segura del ViewModel
    val viewModel: SedeViewModel = viewModel(factory = SedeViewModelFactory(context))

    val distancia by viewModel.distanciaTexto.collectAsState()
    val sedes by viewModel.sedes.collectAsState()

    // Permisos de ubicación
    val launcherPermisos = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.obtenerUbicacion()
        }
    }

    // EFECTO AUTOMÁTICO: AL ENTRAR, PIDE PERMISO Y UBICACIÓN
    LaunchedEffect(Unit) {
        launcherPermisos.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        viewModel.cargarSedes()
        viewModel.obtenerUbicacion() // <-- Esto activa el GPS solo
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Radar de Sede",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(Modifier.height(32.dp))

        // ÍCONO VISUAL
        Icon(
            imageVector = Icons.Default.LocationOn,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.secondary
        )

        Spacer(Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Distancia Calculada:", style = MaterialTheme.typography.labelLarge)
                Spacer(Modifier.height(8.dp))

                Text(
                    text = distancia,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                if (sedes.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Text(sedes[0].direccion, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        Spacer(Modifier.weight(1f))

        if (sedes.isNotEmpty()) {
            Button(
                onClick = {
                    val lat = sedes[0].latitud
                    val lon = sedes[0].longitud
                    val label = sedes[0].nombre
                    val uri = Uri.parse("geo:$lat,$lon?q=$lat,$lon($label)")
                    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Icon(Icons.Default.Map, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Ver en Google Maps")
            }
        }

        Spacer(Modifier.height(16.dp))

        OutlinedButton(
            onClick = { viewModel.obtenerUbicacion() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(Icons.Default.Refresh, contentDescription = null)
            Text(" Actualizar GPS")
        }
    }
}