package com.example.educalink_ev2.navigation

sealed class AppScreens(val route: String) {
    object HomeScreen : AppScreens("home_screen")
    object ProfileScreen : AppScreens("profile_screen")
    object ResourcesScreen : AppScreens("resources_screen")
    object RegistroScreen : AppScreens("registro_screen")

    // 1. Modificamos la ruta de ResumenScreen
    object ResumenScreen : AppScreens("resumen_screen/{nombre}/{email}/{carrera}") {
        // 2. Función helper para construir la ruta con los datos
        fun createRoute(nombre: String, email: String, carrera: String): String {
            return "resumen_screen/$nombre/$email/$carrera"
        }
    }
}