package com.example.vk_kotlin_learning

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel(
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private var _cardList = MutableStateFlow<ArrayList<Int>>(ArrayList())
    var cardList: StateFlow<ArrayList<Int>> = _cardList

    fun AddCard(indexCard: Int) {
        _cardList.update { currentArray ->
            val updateList = ArrayList(currentArray)
            updateList.add(indexCard)
            updateList
        }
    }

}

