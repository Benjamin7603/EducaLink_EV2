package com.example.educalink_ev2.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.RegistroUiState
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PerfilViewModel(private val repository: UsuarioRepository) : ViewModel() {

    // Escuchamos los datos del usuario en tiempo real
    val usuarioState: StateFlow<RegistroUiState> = repository.userData
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RegistroUiState()
        )

    private val _fotoUri = MutableStateFlow<Uri?>(null)
    val fotoUri = _fotoUri.asStateFlow()

    init {
        viewModelScope.launch {
            repository.userData.collect { datos ->
                if (!datos.fotoUri.isNullOrBlank()) {
                    try {
                        _fotoUri.value = Uri.parse(datos.fotoUri)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun guardarFoto(uri: String) {
        _fotoUri.value = Uri.parse(uri)
        viewModelScope.launch {
            repository.guardarFotoUri(uri)
        }
    }

    fun cerrarSesion() {
        viewModelScope.launch {
            repository.cerrarSesion()
        }
    }
}