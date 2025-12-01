package com.example.educalink_ev2.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.RegistroUiState
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    val carrerasDisponibles = listOf(
        "Ingeniería en Informática",
        "Salud",
        "Construcción",
        "Ingeniería y Recursos Naturales",
        "Comunicación",
        "Turismo y Hospitalidad",
        "Gastronomía"
    )

    private val _uiState = MutableStateFlow(RegistroUiState())
    val uiState = _uiState.asStateFlow()

    // --- Funciones de Validación (Igual que antes) ---
    fun onNombreChange(nombre: String) {
        val error = if (nombre.isBlank()) "El nombre no puede estar vacío" else null
        _uiState.update { it.copy(nombre = nombre, errorNombre = error) }
        validarFormulario()
    }

    fun onEmailChange(email: String) {
        val error = if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) "Email inválido" else null
        _uiState.update { it.copy(email = email, errorEmail = error) }
        validarFormulario()
    }

    fun onCarreraChange(carrera: String) {
        val error = if (carrera.isBlank()) "Debe seleccionar una carrera" else null
        _uiState.update { it.copy(carrera = carrera, errorCarrera = error) }
        validarFormulario()
    }

    fun onContrasenaChange(contrasena: String) {
        val error = if (contrasena.length < 6) "Mínimo 6 caracteres" else null
        _uiState.update { it.copy(contrasena = contrasena, errorContrasena = error) }
        validarContrasenasCoinciden()
        validarFormulario()
    }

    fun onRepetirContrasenaChange(repetirContrasena: String) {
        _uiState.update { it.copy(repetirContrasena = repetirContrasena) }
        validarContrasenasCoinciden()
        validarFormulario()
    }

    private fun validarContrasenasCoinciden() {
        _uiState.update {
            val error = if (it.contrasena != it.repetirContrasena) "Las contraseñas no coinciden" else null
            it.copy(errorRepetirContrasena = error)
        }
    }

    private fun validarFormulario() {
        _uiState.update {
            val esValido = it.errorNombre == null &&
                    it.errorEmail == null &&
                    it.errorContrasena == null &&
                    it.errorRepetirContrasena == null &&
                    it.nombre.isNotBlank() &&
                    it.email.isNotBlank() &&
                    it.carrera.isNotBlank() &&
                    it.contrasena.length >= 6 &&
                    it.contrasena == it.repetirContrasena
            it.copy(esValido = esValido)
        }
    }

    // --- FUNCIÓN DE GUARDADO CON FIREBASE ---
    fun guardarRegistro(onSuccess: () -> Unit) {
        if (!_uiState.value.esValido) return

        viewModelScope.launch {
            // Llamamos al repositorio que ahora conecta con Firebase
            val exito = repository.registrarUsuario(
                nombre = _uiState.value.nombre,
                email = _uiState.value.email,
                carrera = _uiState.value.carrera,
                contrasena = _uiState.value.contrasena
            )

            if (exito) {
                // Si Firebase responde TRUE, ejecutamos la navegación
                onSuccess()
            } else {
                // Si falla (ej: correo ya existe), mostramos error en el campo Email
                _uiState.update {
                    it.copy(errorEmail = "Error: El correo ya existe o no hay internet")
                }
            }
        }
    }
}