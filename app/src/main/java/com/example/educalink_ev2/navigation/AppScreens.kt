package com.example.educalink_ev2.navigation

sealed class AppScreens(val route: String) {
    // Pantallas de autenticación
    object AuthLoadingScreen : AppScreens("auth_loading_screen")
    object LoginScreen : AppScreens("login_screen")
    object RegistroScreen : AppScreens("registro_screen")

    // Pantalla contenedora principal
    object MainScreen : AppScreens("main_screen")

    // Pantallas internas (barra de navegación)
    object HomeScreen : AppScreens("home_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object ResourcesScreen : AppScreens("resources_screen")

    // --- ¡RUTA GPS RENOMBRADA! ---
    object EncuentraSedeScreen : AppScreens("encuentra_sede_screen")
}