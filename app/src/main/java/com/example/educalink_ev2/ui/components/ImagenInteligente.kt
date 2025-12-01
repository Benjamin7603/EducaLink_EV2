package com.example.educalink_ev2.ui.components

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter // Si te marca error aquí, avísame (es la librería Coil)

@Composable
fun ImagenInteligente(uri: Uri?) {
    val modifier = Modifier
        .size(120.dp)
        .clip(CircleShape)
        .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)

    if (uri != null && uri.toString().isNotEmpty()) {
        // Intenta mostrar la foto real
        Image(
            painter = rememberAsyncImagePainter(uri),
            contentDescription = "Foto de perfil",
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        // Si no hay foto, muestra el ícono por defecto
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Sin foto",
            modifier = modifier,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}