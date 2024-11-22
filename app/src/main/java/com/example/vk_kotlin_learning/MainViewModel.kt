package com.example.vk_kotlin_learning

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private var _cardList = MutableStateFlow<List<GifData>>(listOf())
    var cardList: StateFlow<List<GifData>> = _cardList.asStateFlow()

    init {
        _cardList.value = listOf()
    }

    fun addGifs(listDates: List<GifData>) {
        _cardList.update { currentList ->
            currentList + listDates
        }
    }

}

