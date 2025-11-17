package com.example.educalink_ev2.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educalink_ev2.network.RetrofitClient
import com.example.educalink_ev2.repository.PostRepository
import com.example.educalink_ev2.viewmodel.ResourcesViewModel
import com.example.educalink_ev2.viewmodel.ResourcesViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen() {
    val context = LocalContext.current

    // 1. Configuramos el ViewModel manualmente (inyección de dependencias simple)
    val apiService = RetrofitClient.instance
    val repository = remember { PostRepository(apiService) }
    val factory = remember { ResourcesViewModelFactory(repository) }
    val viewModel: ResourcesViewModel = viewModel(factory = factory)

    // 2. Observamos los estados
    val noticias by viewModel.noticias.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recursos y Noticias") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // --- SECCIÓN 1: ENLACES (Lo que ya teníamos) ---
            item {
                Text(
                    "Enlaces Estudiantiles",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            item {
                ResourceLinkCard(
                    title = "Sitio Web Institucional",
                    description = "Accede al portal principal de Duoc UC.",
                    icon = Icons.Default.Language,
                    onClick = { openUrlInBrowser("https://www.duoc.cl") }
                )
            }

            item {
                ResourceLinkCard(
                    title = "Aula Virtual (AVA)",
                    description = "Tu portal para ramos, notas y material de estudio.",
                    icon = Icons.Default.School,
                    onClick = { openUrlInBrowser("https://ava.duoc.cl") }
                )
            }

            item {
                ResourceLinkCard(
                    title = "Biblioteca",
                    description = "Busca libros, publicaciones y recursos académicos.",
                    icon = Icons.Default.MenuBook,
                    onClick = { openUrlInBrowser("https://biblioteca.duoc.cl") }
                )
            }

            // --- SECCIÓN 2: NOTICIAS DESDE API (Lo Nuevo) ---
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Noticias Recientes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    if (isLoading) {
                        CircularProgressIndicator(modifier = Modifier.size(20.dp), strokeWidth = 2.dp)
                    }
                }
                Text(
                    "Obtenido de jsonplaceholder.typicode.com",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }

            if (noticias.isEmpty() && !isLoading) {
                item { Text("No se pudieron cargar las noticias.") }
            } else {
                // Iteramos sobre la lista de noticias que viene de la API
                items(noticias) { post ->
                    NoticiaCard(titulo = post.title, cuerpo = post.body)
                }
            }
        }
    }
}

@Composable
fun NoticiaCard(titulo: String, cuerpo: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = titulo.replaceFirstChar { it.uppercase() }, // Capitalizar título
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = cuerpo.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ResourceLinkCard(
    title: String,
    description: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        ListItem(
            headlineContent = { Text(title, style = MaterialTheme.typography.titleMedium) },
            supportingContent = { Text(description, style = MaterialTheme.typography.bodyMedium) },
            leadingContent = {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            trailingContent = {
                TextButton(onClick = onClick) {
                    Text("Abrir")
                }
            }
        )
    }
}