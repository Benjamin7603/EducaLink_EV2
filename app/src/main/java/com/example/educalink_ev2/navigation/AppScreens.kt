package com.example.educalink_ev2.navigation

// sealed class para definir las rutas de navegación
sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object ResourcesScreen : AppScreens("resources_screen")

    // Dejamos listas las pantallas de la Guía 11
    object RegistroScreen : AppScreens("registro_screen")
    object ResumenScreen : AppScreens("resumen_screen")
}