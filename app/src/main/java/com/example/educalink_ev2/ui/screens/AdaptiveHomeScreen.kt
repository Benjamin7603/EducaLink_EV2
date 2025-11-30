package com.example.educalink_ev2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.educalink_ev2.navigation.AppScreens
import com.example.educalink_ev2.viewmodel.HomeViewModel

@Composable
fun AdaptiveHomeScreen(
    windowSizeClass: WindowSizeClass,
    navController: NavController,
    authNavController: NavController,
    homeViewModel: HomeViewModel = viewModel()
) {
    val mensaje by homeViewModel.mensajeBienvenida.collectAsState()
    val isCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    when (isCompact) {
        true -> {
            HomeScreenContent(
                mensaje = mensaje,
                navController = navController,
                authNavController = authNavController,
                modifier = Modifier.padding(16.dp),
                isCompact = true
            )
        }
        false -> {
            HomeScreenContent(
                mensaje = mensaje,
                navController = navController,
                authNavController = authNavController,
                modifier = Modifier.padding(32.dp),
                isCompact = false
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    mensaje: String,
    navController: NavController,
    authNavController: NavController,
    modifier: Modifier = Modifier,
    isCompact: Boolean
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = mensaje,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Acciones Rápidas",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    @Composable
                    fun QuickActionButton(
                        text: String,
                        icon: ImageVector,
                        onClick: () -> Unit,
                        modifier: Modifier = Modifier
                    ) {
                        Button(
                            onClick = onClick,
                            modifier = modifier,
                            shape = MaterialTheme.shapes.medium,
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Icon(icon, contentDescription = text)
                            Spacer(Modifier.width(12.dp))
                            Text(text, style = MaterialTheme.typography.labelLarge)
                        }
                    }

                    if (isCompact) {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            QuickActionButton(
                                text = "Mi Perfil",
                                icon = Icons.Default.AccountCircle,
                                onClick = { navController.navigate(AppScreens.ProfileScreen.route) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            QuickActionButton(
                                text = "Recursos",
                                icon = Icons.Default.Search,
                                onClick = { navController.navigate(AppScreens.ResourcesScreen.route) },
                                modifier = Modifier.fillMaxWidth()
                            )
                            QuickActionButton(
                                text = "Encuentra tu Sede",
                                icon = Icons.Default.Place,
                                onClick = { navController.navigate(AppScreens.EncuentraSedeScreen.route) },
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    } else {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            QuickActionButton(
                                text = "Mi Perfil",
                                icon = Icons.Default.AccountCircle,
                                onClick = { navController.navigate(AppScreens.ProfileScreen.route) }
                            )
                            QuickActionButton(
                                text = "Recursos",
                                icon = Icons.Default.Search,
                                onClick = { navController.navigate(AppScreens.ResourcesScreen.route) }
                            )
                            QuickActionButton(
                                text = "Encuentra tu Sede",
                                icon = Icons.Default.Place,
                                onClick = { navController.navigate(AppScreens.EncuentraSedeScreen.route) }
                            )
                        }
                    }

                    Spacer(Modifier.height(24.dp))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Anuncios Importantes",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    ListItem(
                        headlineContent = {
                            Text("Fechas de Exámenes Finales", fontWeight = FontWeight.SemiBold)
                        },
                        supportingContent = {
                            Text("Las fechas se publicarán el 20 de Diciembre.")
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Anuncio",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                    HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    ListItem(
                        headlineContent = {
                            Text("Suspensión de clases", fontWeight = FontWeight.SemiBold)
                        },
                        supportingContent = {
                            Text("No hay clases el Lunes por mantención.")
                        },
                        leadingContent = {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = "Anuncio",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    )
                }
            }
        }
    }
}
