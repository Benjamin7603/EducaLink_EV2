package com.example.educalink_ev2.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.educalink_ev2.model.RegistroUiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_settings")

class UsuarioRepository(private val context: Context) {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    companion object {
        val CARRERA_KEY = stringPreferencesKey("carrera_usuario")
        val FOTO_URI_KEY = stringPreferencesKey("foto_uri")
    }

    // --- FUNCIÓN QUE USA 'AuthLoadingScreen' ---
    fun isUserLoggedIn(): Boolean {
        return auth.currentUser != null
    }

    // 1. REGISTRO
    suspend fun registrarUsuario(nombre: String, email: String, carrera: String, contrasena: String): Boolean {
        return try {
            val resultado = auth.createUserWithEmailAndPassword(email, contrasena).await()
            val usuarioFirebase = resultado.user
            val profileUpdates = userProfileChangeRequest { displayName = nombre }
            usuarioFirebase?.updateProfile(profileUpdates)?.await()

            // Aquí usamos dataStore (funciona gracias a la línea de arriba)
            context.dataStore.edit { prefs -> prefs[CARRERA_KEY] = carrera }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // 2. LOGIN
    suspend fun loginUsuario(email: String, contrasena: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, contrasena).await()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    // 3. CERRAR SESIÓN
    suspend fun cerrarSesion() {
        auth.signOut()
        context.dataStore.edit { it.clear() }
    }

    // 4. DATOS DEL USUARIO (Aquí te daba el error rojo)
    val userData: Flow<RegistroUiState> = flow {
        // Al recolectar del dataStore, necesitamos que la línea del principio del archivo exista
        context.dataStore.data.collect { prefs ->
            val user = auth.currentUser
            if (user != null) {
                emit(RegistroUiState(
                    nombre = user.displayName ?: "Usuario",
                    email = user.email ?: "",
                    carrera = prefs[CARRERA_KEY] ?: "Carrera no especificada",
                    fotoUri = prefs[FOTO_URI_KEY] ?: "",
                    esValido = true
                ))
            } else {
                emit(RegistroUiState())
            }
        }
    }

    // 5. GUARDAR FOTO
    suspend fun guardarFotoUri(fotoUri: String) {
        context.dataStore.edit { settings -> settings[FOTO_URI_KEY] = fotoUri }
    }
}