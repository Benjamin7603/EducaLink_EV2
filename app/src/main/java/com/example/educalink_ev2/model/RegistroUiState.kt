package com.example.educalink_ev2.model

data class RegistroUiState(
    // Datos del formulario
    val nombre: String = "",
    val email: String = "",
    val carrera: String = "",
    val contrasena: String = "",
    val repetirContrasena: String = "",

    // Foto de perfil (URI guardada como texto)
    val fotoUri: String = "",

    // Estado general del botón
    val esValido: Boolean = false,

    // Mensajes de Error (Validaciones)
    val errorNombre: String? = null,
    val errorEmail: String? = null,
    val errorCarrera: String? = null, // <--- ¡ESTE ERA EL QUE FALTABA!
    val errorContrasena: String? = null,
    val errorRepetirContrasena: String? = null
)