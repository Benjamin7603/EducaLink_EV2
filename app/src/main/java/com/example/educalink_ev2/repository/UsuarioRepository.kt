package com.example.educalink_ev2.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.educalink_ev2.model.RegistroUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// 1. Define el "almacén" de datos
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class UsuarioRepository(private val context: Context) {

    // 2. Define las "llaves" para cada dato que guardaremos
    companion object {
        val NOMBRE_KEY = stringPreferencesKey("nombre_usuario")
        val EMAIL_KEY = stringPreferencesKey("email_usuario")
        val CARRERA_KEY = stringPreferencesKey("carrera_usuario")
    }

    // 3. Función para GUARDAR los datos del formulario
    suspend fun guardarDatos(nombre: String, email: String, carrera: String) {
        context.dataStore.edit { settings ->
            settings[NOMBRE_KEY] = nombre
            settings[EMAIL_KEY] = email
            settings[CARRERA_KEY] = carrera
        }
    }

    // 4. Flow para LEER los datos (se usará en la Guía 13)
    val userData: Flow<RegistroUiState> = context.dataStore.data
        .map { preferences ->
            RegistroUiState(
                nombre = preferences[NOMBRE_KEY] ?: "",
                email = preferences[EMAIL_KEY] ?: "",
                carrera = preferences[CARRERA_KEY] ?: ""
            )
        }
}