package com.example.educalink_ev2.ui.screens

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.rememberNavController
import com.example.educalink_ev2.repository.UsuarioRepository
import com.example.educalink_ev2.viewmodel.LoginViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class LoginScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun verificar_elementos_login_visibles() {
        // 1. Preparamos los mocks (falsos) para que la pantalla no falle
        val repositorioFalso = mockk<UsuarioRepository>(relaxed = true)
        val viewModelFalso = LoginViewModel(repositorioFalso)

        // 2. Cargamos la pantalla en el entorno de prueba
        composeTestRule.setContent {
            val navController = rememberNavController()
            LoginScreen(
                navController = navController,
                viewModel = viewModelFalso
            )
        }

        // 3. Verificamos que los textos existan en la pantalla
        // Busca el texto del título (Asegúrate que coincida con tu LoginScreen)
        composeTestRule.onNodeWithText("Bienvenido a EducaLink").assertIsDisplayed()

        // Busca el campo de correo
        composeTestRule.onNodeWithText("Correo Electrónico").assertIsDisplayed()

        // Busca el botón
        composeTestRule.onNodeWithText("Ingresar").assertIsDisplayed()
    }
}