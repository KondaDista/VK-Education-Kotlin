package com.example.vk_kotlin_learning.giphy_api

import com.example.vk_kotlin_learning.GifsList
import retrofit2.Retrofit
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Response

class GiphyController() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GIPHY)
        .addConverterFactory(Json {ignoreUnknownKeys = true}.asConverterFactory("application/json; charset=UTF8".toMediaType()))
        .build()

    private val giphyAPI = retrofit.create(GiphyAPI::class.java)

    suspend fun requestTrendingGif(count: Int, offset: Int): Response<GifsList>? {
        val response = giphyAPI.GetTrendingGifs(API, count, offset)
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null) {
                return response
            }
        }
        return null
    }

    companion object {
        const val API = "1YfaSYYa0OyUaGUs9r6UKOwXUd7svBzj"
        const val GIPHY = "https://api.giphy.com"
    }
}