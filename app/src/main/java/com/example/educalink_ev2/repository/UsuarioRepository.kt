package com.example.educalink_ev2.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.educalink_ev2.model.RegistroUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UsuarioRepository(private val context: Context) {

    companion object {
        val NOMBRE_KEY = stringPreferencesKey("nombre_usuario")
        val CARRERA_KEY = stringPreferencesKey("carrera_usuario")
        // --- NUEVAS LLAVES AÑADIDAS ---
        val EMAIL_REGISTRADO_KEY = stringPreferencesKey("email_registrado")
        val PASSWORD_REGISTRADO_KEY = stringPreferencesKey("password_registrado")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")
    }

    val userData: Flow<RegistroUiState> = context.dataStore.data
        .map { preferences ->
            RegistroUiState(
                nombre = preferences[NOMBRE_KEY] ?: "",
                email = preferences[EMAIL_REGISTRADO_KEY] ?: "", // Usa el email guardado
                carrera = preferences[CARRERA_KEY] ?: ""
            )
        }

    // Flow para saber si el usuario está logueado
    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    // Función para GUARDAR el NUEVO registro
    suspend fun guardarRegistro(nombre: String, email: String, carrera: String, contrasena: String) {
        context.dataStore.edit { settings ->
            settings[NOMBRE_KEY] = nombre
            settings[CARRERA_KEY] = carrera
            settings[EMAIL_REGISTRADO_KEY] = email
            settings[PASSWORD_REGISTRADO_KEY] = contrasena
            settings[IS_LOGGED_IN_KEY] = false // Se registró, pero no ha iniciado sesión
        }
    }

    // Función para VERIFICAR el Login
    suspend fun verificarLogin(emailIngresado: String, contrasenaIngresada: String): Boolean {
        val preferences = context.dataStore.data.first() // Lee los datos una vez
        val emailGuardado = preferences[EMAIL_REGISTRADO_KEY]
        val contrasenaGuardada = preferences[PASSWORD_REGISTRADO_KEY]

        val esValido = emailGuardado == emailIngresado && contrasenaGuardada == contrasenaIngresada

        if (esValido) {
            // Si el login es correcto, marcamos la sesión como activa
            context.dataStore.edit { settings ->
                settings[IS_LOGGED_IN_KEY] = true
            }
        }
        return esValido
    }

    // Función para CERRAR SESIÓN
    suspend fun cerrarSesion() {
        context.dataStore.edit { settings ->
            settings[IS_LOGGED_IN_KEY] = false
        }
    }
}