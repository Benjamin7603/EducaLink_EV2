package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.repository.UsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: UsuarioRepository
) : ViewModel() {

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val mensaje: String) : LoginState()
    }

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState = _loginState.asStateFlow()

    fun iniciarLogin(email: String, contrasena: String) {
        // Validación básica antes de llamar a la nube
        if (email.isBlank() || contrasena.isBlank()) {
            _loginState.value = LoginState.Error("Email y contraseña son obligatorios")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            // --- AQUÍ EL CAMBIO CLAVE ---
            // Llamamos a loginUsuario (Firebase) en vez de verificarLogin (Local)
            val loginExitoso = repository.loginUsuario(email, contrasena)

            if (loginExitoso) {
                _loginState.value = LoginState.Success
            } else {
                // Firebase falló (Credenciales malas, usuario no existe o sin internet)
                _loginState.value = LoginState.Error("Error: Credenciales incorrectas o sin conexión")
            }
        }
    }
}