package com.example.vk_kotlin_learning

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyAPI {

    @GET("v1/gifs/trending")
    suspend fun GetTrendingGifs(@Query("api_key") apiKey: String, @Query("limit") limit: Int): Response<GifsList>
}