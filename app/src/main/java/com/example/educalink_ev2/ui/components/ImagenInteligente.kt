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
import coil.compose.AsyncImage

@Composable
fun ImagenInteligente(
    uri: Uri?, // La URI de la foto
    modifier: Modifier = Modifier
) {
    if (uri != null) {
        // Si SÍ hay URI, muestra la foto con Coil
        AsyncImage(
            model = uri,
            contentDescription = "Foto de perfil",
            modifier = modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
            contentScale = ContentScale.Crop
        )
    } else {
        // Si NO hay URI, muestra un ícono
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Icono de perfil",
            modifier = modifier.size(150.dp)
        )
    }
}