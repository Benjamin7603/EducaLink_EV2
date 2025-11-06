package com.example.educalink_ev2.model

data class RegistroUiState(
    val nombre: String = "",
    val email: String = "",
    val carrera: String = "",
    val contrasena: String = "",
    val repetirContrasena: String = "",

    // Define los campos de error como Strings opcionales (String?)
    val errorNombre: String? = null,
    val errorEmail: String? = null,
    val errorContrasena: String? = null,
    val errorRepetirContrasena: String? = null,

    val esValido: Boolean = false
)