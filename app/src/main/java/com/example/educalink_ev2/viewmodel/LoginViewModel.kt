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
        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            if (email.isBlank() || contrasena.isBlank()) {
                _loginState.value = LoginState.Error("Email y contraseña no pueden estar vacíos")
                return@launch
            }

            val esValido = repository.verificarLogin(email, contrasena)

            if (esValido) {
                _loginState.value = LoginState.Success
            } else {
                _loginState.value = LoginState.Error("Email o contraseña incorrectos")
            }
        }
    }
}