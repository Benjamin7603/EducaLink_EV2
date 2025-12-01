package com.example.educalink_ev2.viewmodel

import com.example.educalink_ev2.repository.UsuarioRepository
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test // <--- ESTO ES LO CRÍTICO: Debe ser org.junit.Test (JUnit 4)

@OptIn(ExperimentalCoroutinesApi::class)
class RegistroViewModelTest {

    // Mock del repositorio (Simulado)
    private val repository: UsuarioRepository = mockk(relaxed = true)

    private lateinit var viewModel: RegistroViewModel

    // Dispatcher para pruebas (hace que las corrutinas corran al instante)
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RegistroViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun al_ingresar_email_invalido_debe_mostrar_error() {
        // GIVEN
        val emailMalo = "esto-no-es-un-correo"

        // WHEN
        viewModel.onEmailChange(emailMalo)

        // THEN
        val estado = viewModel.uiState.value
        assertEquals("Email inválido", estado.errorEmail)
        assertFalse(estado.esValido)
    }

    @Test
    fun al_ingresar_nombre_vacio_debe_mostrar_error() {
        // GIVEN
        val nombreVacio = ""

        // WHEN
        viewModel.onNombreChange(nombreVacio)

        // THEN
        val estado = viewModel.uiState.value
        assertEquals("El nombre no puede estar vacío", estado.errorNombre)
    }
}