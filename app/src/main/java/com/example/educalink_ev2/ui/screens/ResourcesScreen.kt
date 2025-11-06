package com.example.educalink_ev2.ui.screens

// 1. IMPORTS NUEVOS QUE NECESITAMOS
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
// (El resto de tus imports están bien)
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LaptopChromebook
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector // Import para el ícono
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResourcesScreen() {
    val uriHandler = LocalUriHandler.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recursos y Contacto") }
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp) // Añadimos padding aquí
        ) {

            item {
                Text(
                    "Enlaces Importantes",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // --- ESTE ES EL NUEVO CÓDIGO (YA NO USA ListItem) ---
            item {
                ClickableRow(
                    text = "Sitio Web Institucional",
                    icon = Icons.Filled.Language,
                    onClick = { uriHandler.openUri("https://www.tu-institucion.com") }
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                ClickableRow(
                    text = "Aula Virtual (Moodle)",
                    icon = Icons.Filled.LaptopChromebook,
                    onClick = { uriHandler.openUri("https://moodle.tu-institucion.com") }
                )
            }
            // --- FIN DEL CÓDIGO NUEVO ---

            item {
                Text(
                    "Contacto",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
                )
            }

            // --- ESTOS TAMBIÉN LOS CAMBIAMOS (YA NO USAN ListItem) ---
            item {
                InfoRow(
                    text = "Secretaría Académica",
                    supportingText = "secretaria@tu-institucion.com",
                    icon = Icons.Filled.Email
                )
                Spacer(Modifier.height(8.dp))
            }

            item {
                InfoRow(
                    text = "Soporte TI",
                    supportingText = "+56 9 1234 5678",
                    icon = Icons.Filled.Phone
                )
            }
        }
    }
}

/**
 * Nuestro propio Composable Clickeable (reemplazo de ListItem con onClick)
 */
@Composable
private fun ClickableRow(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)) // Redondea las esquinas
            .background(MaterialTheme.colorScheme.secondaryContainer) // Color de fondo
            .clickable(onClick = onClick) // ¡AQUÍ ESTÁ EL ONCLICK!
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
        Spacer(Modifier.width(16.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.weight(1f)
        )
        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = MaterialTheme.colorScheme.onSecondaryContainer)
    }
}

/**
 * Nuestro propio Composable de Info (reemplazo de ListItem sin onClick)
 */
@Composable
private fun InfoRow(
    text: String,
    supportingText: String,
    icon: ImageVector
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(16.dp))
        Column {
            Text(text, style = MaterialTheme.typography.bodyLarge)
            Text(supportingText, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.outline)
        }
    }
}