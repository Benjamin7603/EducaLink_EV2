package com.example.educalink_ev2.repository

import com.example.educalink_ev2.model.Post
import com.example.educalink_ev2.network.PostApiService

class PostRepository(private val apiService: PostApiService) {
    suspend fun obtenerPosts(): List<Post> = apiService.getPosts()
}