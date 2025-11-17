package com.example.educalink_ev2.model

// Define la estructura de los datos que vienen de la API
data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)