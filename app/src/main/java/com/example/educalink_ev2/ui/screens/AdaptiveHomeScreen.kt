package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens

@Composable
fun AdaptiveHomeScreen(
    windowSizeClass: WindowSizeClass,
    navController: NavController,
    authNavController: NavController
) {
    // Detectamos si es celular (Compact)
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    // --- AQUÍ ESTABA EL ERROR ---
    // Borramos el Scaffold y el BottomBar de aquí, porque MainScreen ya lo tiene.
    // Solo dejamos el contenido puro.

    Row(modifier = Modifier.fillMaxSize()) {

        // Barra lateral (Solo para Tablets/Horizontal)
        if (!isCompact) {
            NavigationRail {
                NavigationRailItem(
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Inicio") },
                    selected = true,
                    onClick = {}
                )
            }
        }

        // CONTENIDO PRINCIPAL (LISTA)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Bienvenido a EducaLink",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            // TARJETA GPS
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clickable {
                            navController.navigate(AppScreens.EncuentraSedeScreen.route)
                        },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.tertiaryContainer),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("📍 Radar de Sede", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text("Ver distancia y ubicación", style = MaterialTheme.typography.bodyMedium)
                        }
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Mapa",
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }
            }

            // TARJETA NOTICIAS
            item {
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Notifications, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Cartelera", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        }
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        ListItem(
                            headlineContent = { Text("Suspensión de clases") },
                            supportingContent = { Text("Viernes cerrado por mantención.") },
                            leadingContent = { Icon(Icons.Default.Warning, contentDescription = null) }
                        )
                    }
                }
            }
        }
    }
}