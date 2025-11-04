package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.educalink_ev2.model.RegistroUiState

class RegistroViewModel : ViewModel() {

    // 1. El StateFlow privado y mutable que contiene el estado
    private val _uiState = MutableStateFlow(RegistroUiState())

    // 2. El StateFlow público e inmutable que la UI observará
    val uiState = _uiState.asStateFlow()

    // 3. Funciones que actualizan el estado cuando el usuario escribe
    fun onNombreChange(nombre: String) {
        _uiState.update { currentState ->
            currentState.copy(nombre = nombre)
        }
    }

    fun onEmailChange(email: String) {
        _uiState.update { currentState ->
            currentState.copy(email = email)
        }
    }

    fun onCarreraChange(carrera: String) {
        _uiState.update { currentState ->
            currentState.copy(carrera = carrera)
        }
    }

    // (En la Guía 12 agregaremos la lógica para "guardar" aquí)
}