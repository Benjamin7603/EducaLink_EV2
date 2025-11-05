package com.example.educalink_ev2.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.R
import com.example.educalink_ev2.model.RegistroUiState
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.io.File

class PerfilViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    // 1. Estado para los datos del usuario (leídos de DataStore)
    private val _usuarioState = MutableStateFlow(RegistroUiState())
    val usuarioState = _usuarioState.asStateFlow()

    // 2. Estado para la URI de la foto
    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri = _fotoUri.asStateFlow()

    init {
        // 3. Cuando el ViewModel se inicie, empieza a observar DataStore
        viewModelScope.launch {
            repository.userData.collect { userData ->
                _usuarioState.value = userData
            }
        }
    }

    // 4. Actualiza la URI de la foto en el estado
    fun onFotoTomada(uri: Uri) {
        _fotoUri.value = uri
    }

    // 5. Crea un archivo temporal y su URI para que la cámara guarde la foto
    fun getUriTemporal(context: Context): Uri {
        val file = File(context.cacheDir, "images/foto_perfil.png")
        if (!file.parentFile!!.exists()) file.parentFile!!.mkdirs()

        val authority = "${context.packageName}.provider"
        val uri = FileProvider.getUriForFile(context, authority, file)

        // Guarda esta URI para usarla después de tomar la foto
        onFotoTomada(uri)

        return uri
    }
}