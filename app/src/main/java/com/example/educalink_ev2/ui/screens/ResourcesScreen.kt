package com.example.educalink_ev2.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen() {
    val context = LocalContext.current

    // 1. Esta es la función mágica que abre el navegador
    fun openUrlInBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Recursos Estudiantiles") })
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // 2. Tarjeta para el Sitio Web Institucional
            item {
                ResourceLinkCard(
                    title = "Sitio Web Institucional",
                    description = "Accede al portal principal de Duoc UC.",
                    icon = Icons.Default.Language,
                    onClick = {
                        openUrlInBrowser("https://www.duoc.cl")
                    }
                )
            }

            // 3. Tarjeta para el Aula Virtual (AVA)
            item {
                ResourceLinkCard(
                    title = "Aula Virtual (AVA)",
                    description = "Tu portal para ramos, notas y material de estudio.",
                    icon = Icons.Default.School,
                    onClick = {
                        openUrlInBrowser("https://ava.duoc.cl")
                    }
                )
            }

            // 4. (Opcional) Añadí la biblioteca, que también es un recurso clave
            item {
                ResourceLinkCard(
                    title = "Biblioteca",
                    description = "Busca libros, publicaciones y recursos académicos.",
                    icon = Icons.Default.MenuBook,
                    onClick = {
                        openUrlInBrowser("https://biblioteca.duoc.cl")
                    }
                )
            }
        }
    }
}

/**
 * Un Composable reutilizable para mostrar cada enlace en una tarjeta.
 */
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