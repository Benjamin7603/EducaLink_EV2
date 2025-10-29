package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel : ViewModel() {

    // Estado privado mutable
    private val _mensajeBienvenida = MutableStateFlow("Bienvenido a EducaLink!")

    // Estado público inmutable (solo lectura) para la UI
    val mensajeBienvenida = _mensajeBienvenida.asStateFlow()

    // Lógica futura (ej. cargar datos) puede ir aquí
}