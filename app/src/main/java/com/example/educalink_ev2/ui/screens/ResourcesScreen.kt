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

    // Configuración Manual de Inyección de Dependencias
    val apiService = RetrofitClient.instance
    val repository = remember { PostRepository(apiService) }
    val factory = remember { ResourcesViewModelFactory(repository) }
    val viewModel: ResourcesViewModel = viewModel(factory = factory)

    val noticias by viewModel.noticias.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Recursos y Noticias") }) }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // SECCIÓN 1: Enlaces
            item {
                Text("Enlaces Rápidos", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            }
            item {
                ResourceLinkCard("Sitio Web Duoc", "Portal Institucional", Icons.Default.Language) { openUrlInBrowser("https://www.duoc.cl") }
            }
            item {
                ResourceLinkCard("Aula Virtual (AVA)", "Tus asignaturas", Icons.Default.School) { openUrlInBrowser("https://ava.duoc.cl") }
            }

            // SECCIÓN 2: API Rest
            item {
                Spacer(Modifier.height(16.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Noticias (API)", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                    if (isLoading) CircularProgressIndicator(modifier = Modifier.padding(start = 8.dp).size(20.dp))
                }
            }

            if (noticias.isEmpty() && !isLoading) {
                item { Text("No hay noticias disponibles.") }
            } else {
                items(noticias) { post ->
                    NoticiaCard(post.title, post.body)
                }
            }
        }
    }
}

@Composable
fun NoticiaCard(titulo: String, cuerpo: String) {
    Card(modifier = Modifier.fillMaxWidth(), elevation = CardDefaults.cardElevation(2.dp)) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo.uppercase(), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(cuerpo, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourceLinkCard(title: String, desc: String, icon: ImageVector, onClick: () -> Unit) {
    Card(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        ListItem(
            headlineContent = { Text(title) },
            supportingContent = { Text(desc) },
            leadingContent = { Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary) }
        )
    }
}