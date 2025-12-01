package com.example.educalink_ev2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educalink_ev2.model.Post
import com.example.educalink_ev2.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ResourcesViewModel(private val repository: PostRepository) : ViewModel() {

    private val _noticias = MutableStateFlow<List<Post>>(emptyList())
    val noticias = _noticias.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    init {
        cargarNoticias()
    }

    fun cargarNoticias() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Traemos solo 5 para que se vea ordenado
                _noticias.value = repository.obtenerPosts().take(5)
            } catch (e: Exception) {
                _noticias.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }
}