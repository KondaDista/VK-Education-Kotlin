package com.example.vk_kotlin_learning.giphy_api

import com.example.vk_kotlin_learning.GifsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GiphyAPI {

    @GET("v1/gifs/trending")
    suspend fun GetTrendingGifs(@Query("api_key") apiKey: String, @Query("limit") limit: Int, @Query("offset") offset: Int): Response<GifsList>
}