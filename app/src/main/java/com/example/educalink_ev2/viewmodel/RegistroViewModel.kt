package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.RegistroUiState
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. El ViewModel ahora recibe el repositorio en su constructor
class RegistroViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()

    // ... (las funciones onNombreChange, onEmailChange, onCarreraChange
    //      quedan exactamente IGUALES que en la Guía 11) ...

    fun onNombreChange(nombre: String) {
        _uiState.update { it.copy(nombre = nombre) }
    }
    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email) }
    }
    fun onCarreraChange(carrera: String) {
        _uiState.update { it.copy(carrera = carrera) }
    }

    // 2. Nueva función para guardar los datos usando el repositorio
    fun guardarDatos() {
        viewModelScope.launch {
            repository.guardarDatos(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                carrera = _uiState.value.carrera
            )
        }
    }
}