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
        val EMAIL_REGISTRADO_KEY = stringPreferencesKey("email_registrado")
        val PASSWORD_REGISTRADO_KEY = stringPreferencesKey("password_registrado")
        val IS_LOGGED_IN_KEY = booleanPreferencesKey("is_logged_in")

        // --- ¡NUEVA LLAVE PARA LA FOTO! ---
        val FOTO_URI_KEY = stringPreferencesKey("foto_uri")
    }

    // El flow AHORA TAMBIÉN EMITE la URI de la foto
    val userData: Flow<RegistroUiState> = context.dataStore.data
        .map { preferences ->
            RegistroUiState(
                nombre = preferences[NOMBRE_KEY] ?: "",
                email = preferences[EMAIL_REGISTRADO_KEY] ?: "",
                carrera = preferences[CARRERA_KEY] ?: "",
                // --- ¡CARGAMOS LA URI GUARDADA! ---
                fotoUri = preferences[FOTO_URI_KEY] ?: ""
            )
        }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data
        .map { preferences ->
            preferences[IS_LOGGED_IN_KEY] ?: false
        }

    suspend fun guardarRegistro(nombre: String, email: String, carrera: String, contrasena: String) {
        context.dataStore.edit { settings ->
            settings[NOMBRE_KEY] = nombre
            settings[CARRERA_KEY] = carrera
            settings[EMAIL_REGISTRADO_KEY] = email
            settings[PASSWORD_REGISTRADO_KEY] = contrasena
            settings[IS_LOGGED_IN_KEY] = false
        }
    }

    // --- ¡NUEVA FUNCIÓN PARA GUARDAR SÓLO LA FOTO! ---
    suspend fun guardarFotoUri(fotoUri: String) {
        context.dataStore.edit { settings ->
            settings[FOTO_URI_KEY] = fotoUri
        }
    }

    suspend fun verificarLogin(emailIngresado: String, contrasenaIngresada: String): Boolean {
        val preferences = context.dataStore.data.first()
        val emailGuardado = preferences[EMAIL_REGISTRADO_KEY]
        val contrasenaGuardada = preferences[PASSWORD_REGISTRADO_KEY]

        val esValido = emailGuardado == emailIngresado && contrasenaGuardada == contrasenaIngresada

        if (esValido) {
            context.dataStore.edit { settings ->
                settings[IS_LOGGED_IN_KEY] = true
            }
        }
        return esValido
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { settings ->
            settings[IS_LOGGED_IN_KEY] = false
        }
    }
}