package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educalink_ev2.R
import com.example.educalink_ev2.ui.theme.EducaLink_EV2Theme
import com.example.educalink_ev2.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdaptiveHomeScreen(
    windowSizeClass: WindowSizeClass, // 1. Recibe la clase de tamaño
    homeViewModel: HomeViewModel = viewModel() // 2. Obtiene el ViewModel
) {
    // 3. Observa el mensaje del ViewModel
    val mensaje by homeViewModel.mensajeBienvenida.collectAsState()

    // 4. Decide qué layout mostrar basado en el ancho
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenCompact(mensaje = mensaje) // Layout para móviles
        }
        else -> {
            HomeScreenMedium(mensaje = mensaje) // Layout para tablets (Medium y Expanded)
        }
    }
}

// --- LAYOUT PARA MÓVILES (Compacto) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompact(mensaje: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("EducaLink (Móvil)") })
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Text(
                text = mensaje, // Usa el mensaje del ViewModel
                style = MaterialTheme.typography.headlineMedium
            )
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.fillMaxWidth().height(200.dp),
                contentScale = ContentScale.Fit
            )
            Button(onClick = { /* Acción futura */ }) {
                Text("Comenzar")
            }
        }
    }
}

// --- LAYOUT PARA TABLETS (Mediano) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMedium(mensaje: String, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("EducaLink (Tablet)") })
        }
    ) { innerPadding ->
        Row( // La gran diferencia: usamos Row (horizontal) en lugar de Column
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Contenido a la izquierda (Texto y Botón)
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = mensaje, // Usa el mensaje del ViewModel
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(Modifier.height(24.dp))
                Button(onClick = { /* Acción futura */ }) {
                    Text("Comenzar")
                }
            }

            // Contenido a la derecha (Imagen)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(0.6f),
                contentScale = ContentScale.Fit
            )
        }
    }
}

// --- PREVIEWS (Vistas Previas) ---
@Preview(showBackground = true, widthDp = 360, heightDp = 640) // Móvil
@Composable
fun HomeScreenCompactPreview() {
    EducaLink_EV2Theme {
        HomeScreenCompact(mensaje = "Preview Móvil")
    }
}

@Preview(showBackground = true, widthDp = 840, heightDp = 900) // Tablet
@Composable
fun HomeScreenMediumPreview() {
    EducaLink_EV2Theme {
        HomeScreenMedium(mensaje = "Preview Tablet")
    }
}