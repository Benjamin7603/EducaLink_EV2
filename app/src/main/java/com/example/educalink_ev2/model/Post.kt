package com.example.educalink_ev2.model

data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int? = null
)