package com.example.pal.data.remote_db.Retrofit

import com.example.pal.data.models.Cat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CatsService {

    @GET("cats")
    suspend fun getAllCats(): Response<List<Cat>>

    @GET("cats/{name}")
    suspend fun getCat(@Path("name")name: String): Response<Cat>

}

