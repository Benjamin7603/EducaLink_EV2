package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
// 1. IMPORT QUE FALTABA
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.viewmodel.HomeViewModel


@Composable
fun AdaptiveHomeScreen(
    windowSizeClass: WindowSizeClass,
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val mensaje by homeViewModel.mensajeBienvenida.collectAsState()

    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            HomeScreenContent(
                mensaje = mensaje,
                navController = navController,
                modifier = Modifier.padding(16.dp)
            )
        }
        else -> {
            HomeScreenContent(
                mensaje = mensaje,
                navController = navController,
                modifier = Modifier.padding(32.dp)
            )
        }
    }
}

// --- CONTENIDO REAL DE LA PANTALLA ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    mensaje: String,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    // 2. AHORA SÍ RECONOCERÁ LAZYCOLUMN
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        // 3. Y AHORA SÍ RECONOCERÁ ITEM
        item {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Acciones Rápidas",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(onClick = { navController.navigate(AppScreens.ProfileScreen.route) }) {
                            Icon(Icons.Default.AccountCircle, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Mi Perfil")
                        }
                        Button(onClick = { navController.navigate(AppScreens.ResourcesScreen.route) }) {
                            Icon(Icons.Default.Search, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Recursos")
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Anuncios Importantes",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(16.dp))

                    ListItem(
                        headlineContent = { Text("Fechas de Exámenes Finales") },
                        supportingContent = { Text("Las fechas se publicarán el 20 de Diciembre.") },
                        leadingContent = { Icon(Icons.Default.Star, contentDescription = null) }
                    )
                    Divider()
                    ListItem(
                        headlineContent = { Text("Suspensión de clases") },
                        supportingContent = { Text("No hay clases el Lunes por mantención.") },
                        leadingContent = { Icon(Icons.Default.Star, contentDescription = null) }
                    )
                }
            }
        }
    }
}