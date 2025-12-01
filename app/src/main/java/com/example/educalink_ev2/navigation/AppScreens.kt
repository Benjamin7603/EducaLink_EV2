package com.example.educalink_ev2.navigation

sealed class AppScreens(val route: String) {
    // --- PANTALLAS DE AUTENTICACIÓN ---
    object AuthLoadingScreen : AppScreens("auth_loading")
    object LoginScreen : AppScreens("login")
    object RegistroScreen : AppScreens("registro")

    // --- PANTALLAS PRINCIPALES ---
    // Esta es la pantalla "Contenedora" (la que tiene la barra de abajo)
    object MainScreen : AppScreens("main_screen")

    // Pantallas internas del menú
    object ProfileScreen : AppScreens("profile")
    object ResourcesScreen : AppScreens("resources")
    object EncuentraSedeScreen : AppScreens("mapa") // Tu pantalla de GPS

    // ALIAS DE COMPATIBILIDAD:
    // Esto hace que si escribes AppScreens.HomeScreen, en realidad use MainScreen.
    // Así evitamos los errores rojos en otros archivos.
    companion object {
        val HomeScreen = MainScreen
    }
}