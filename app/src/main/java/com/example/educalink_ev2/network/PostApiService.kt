package com.example.educalink_ev2.network

import com.example.educalink_ev2.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. La Interfaz: Define los métodos de la API
interface PostApiService {
    @GET("posts") // El endpoint (https://jsonplaceholder.typicode.com/posts)
    suspend fun getPosts(): List<Post>
}

// 2. El Cliente: Configura Retrofit (Singleton)
object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val instance: PostApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }
}