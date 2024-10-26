package com.example.vk_kotlin_learning

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class CardScreenFragment(
    val viewModel: MainViewModel
): Fragment() {

    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainContent()
            }
        }
    }


    @Suppress("NonSkippableComposable")
    @SuppressLint("MutableCollectionMutableState")
    @Composable
    private fun MainContent(){
        val countItems = remember { mutableStateOf(viewModel.cardList.value) }
        MainScreen(countItems)
    }


    @Suppress("NonSkippableComposable")
    @Composable
    private fun MainScreen(
        countItems:  MutableState<ArrayList<Int>>
    ){
        Column(modifier = Modifier
            .fillMaxSize()
            .safeContentPadding())
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GenerateItemsList()
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                BottomPanel()
            }

        }
    }

    @Suppress("NonSkippableComposable")
    @Composable
    private fun BottomPanel(
    ){
        Row{
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    viewModel.AddCard(viewModel.cardList.value.size)
                },
                colors = ButtonColors(Color.Green, Color.White, Color.Gray, Color.Black)
            ){
                Text(
                    text = "Add item",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Suppress("NonSkippableComposable")
    @Composable
    private fun GenerateItemsList(){

        LazyVerticalGrid(
            modifier = Modifier.padding(5.dp, 5.dp, 5.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(viewModel.cardList.value.size){
                BoxItem(it)
            }
        }
    }


    @Suppress("NonSkippableComposable")
    @Composable
    private fun BoxItem(
        indexItem: Int,
    ){
        val colorsEven: Color = if (indexItem % 2 == 0){
            Color.Red
        } else{
            Color.Blue
        }

        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(1f)
                .background(colorsEven),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = "$indexItem",
                fontSize = 26.sp,
                color = Color.White,
            )
        }
    }
}