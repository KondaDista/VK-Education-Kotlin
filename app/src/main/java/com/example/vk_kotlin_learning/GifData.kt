package com.example.vk_kotlin_learning

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GifsList (
    val data: List<GifData>
)

@Serializable
data class GifData(
    @SerialName("title") val title: String,
    @SerialName("images") val images: Images
)

@Serializable
data class Images(
    @SerialName("original") val original : OrigImage
)

@Serializable
data class OrigImage(
    @SerialName("url") val url : String,
    @SerialName("width") val width : String,
    @SerialName("height") val height : String
)