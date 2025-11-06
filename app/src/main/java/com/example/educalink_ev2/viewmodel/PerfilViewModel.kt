package com.example.educalink_ev2.viewmodel

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _usuarioState = MutableStateFlow(RegistroUiState())
    val usuarioState = _usuarioState.asStateFlow()

    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri = _fotoUri.asStateFlow()

    init {
        viewModelScope.launch {
            repository.userData.collect { userData ->
                _usuarioState.value = userData
            }
        }
    }

    // Esta función AHORA SÍ es llamada por la UI
    fun onFotoTomada(uri: Uri) {
        _fotoUri.value = uri
    }

    fun getUriTemporal(context: Context): Uri {
        val file = File(context.cacheDir, "images/foto_perfil.png")
        if (!file.parentFile!!.exists()) file.parentFile!!.mkdirs()

        val authority = "${context.packageName}.provider"
        val uri = FileProvider.getUriForFile(context, authority, file)


        return uri
    }

    suspend fun cerrarSesion() {
        repository.cerrarSesion()
    }
}