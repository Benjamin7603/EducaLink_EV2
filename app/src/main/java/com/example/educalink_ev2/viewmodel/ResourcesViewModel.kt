package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.Post
import com.example.educalink_ev2.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResourcesViewModel(
    private val repository: PostRepository
) : ViewModel() {

    // Estado de la lista de noticias
    private val _noticias = MutableStateFlow<List<Post>>(emptyList())
    val noticias = _noticias.asStateFlow()

    // Estado de carga (para mostrar un spinner)
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        cargarNoticias()
    }

    fun cargarNoticias() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Llamamos a la API a través del repositorio
                val respuesta = repository.obtenerPosts()
                // Tomamos solo los primeros 5 para no llenar la pantalla
                _noticias.value = respuesta.take(5)
            } catch (e: Exception) {
                // Manejo de error simple (lista vacía o log)
                _noticias.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}