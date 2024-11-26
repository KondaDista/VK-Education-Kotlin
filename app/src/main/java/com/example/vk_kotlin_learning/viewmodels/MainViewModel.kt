package com.example.vk_kotlin_learning.viewmodels

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_kotlin_learning.GifData
import com.example.vk_kotlin_learning.GifsList
import com.example.vk_kotlin_learning.giphy_api.GiphyController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val giphyController by lazy { GiphyController() }

    private var _gifsList = MutableStateFlow<List<GifData>>(listOf())
    var gifsList: StateFlow<List<GifData>> = _gifsList.asStateFlow()

    private var _loading = mutableStateOf(false)
    val loading: State<Boolean> = _loading

    private var _failedRequest = mutableStateOf(false)
    val failedRequest: State<Boolean> = _failedRequest

    private var offset = 0
    private val limit = 25

    init {
        loadingGifs()
    }

    fun addGifs(gifsList: GifsList) {
        _gifsList.update { currentList ->
            currentList + gifsList.data
        }
    }

    fun loadingGifs() {
        viewModelScope.launch {
            _loading.value = true
            _failedRequest.value = false
            try {
                val response = giphyController.requestTrendingGif(
                    count = limit,
                    offset = offset
                )

                if (response != null) {
                    addGifs(response.body()!!)
                    offset += limit
                } else {
                    _failedRequest.value = true
                }
            } catch (e: Exception) {
                _failedRequest.value = true
            } finally {
                _loading.value = false
            }
        }
    }

}

