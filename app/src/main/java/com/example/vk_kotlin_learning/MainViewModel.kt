package com.example.vk_kotlin_learning

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainViewModel: ViewModel() {
    private var _cardList = MutableStateFlow<List<Int>>(listOf())
    var cardList: StateFlow<List<Int>> = _cardList.asStateFlow()

    init {
        _cardList.value = listOf()
    }

    fun addCard(indexCard: Int) {
        _cardList.update { currentList ->
            currentList + indexCard
        }
    }

}

