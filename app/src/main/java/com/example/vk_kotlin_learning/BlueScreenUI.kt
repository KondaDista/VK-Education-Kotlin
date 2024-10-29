package com.example.vk_kotlin_learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun DetailCardScreen(
     indexCard: Int
){
    val colorsEven: Color = if (indexCard % 2 == 0){
        Color.Red
    } else{
        Color.Blue
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorsEven)
            .safeContentPadding(),
        contentAlignment = Alignment.Center
    ){
        Text(text = "IndexCard $indexCard")
    }
}