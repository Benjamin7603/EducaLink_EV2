package com.example.educalink_ev2.network

import com.example.educalink_ev2.model.Post
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import com.example.educalink_ev2.model.Sede
interface PostApiService {
    @GET("eventos")
    suspend fun getPosts(): List<Post>

    @GET("sedes") // <--- NUEVO
    suspend fun getSedes(): List<Sede>
}

object RetrofitClient {
    private const val BASE_URL = "http://10.0.2.2:8080/"

    val instance: PostApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PostApiService::class.java)
    }
}