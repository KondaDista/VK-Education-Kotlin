package com.example.vk_kotlin_learning

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel

class CardScreenFragment(): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val fragmentManager = activity?.supportFragmentManager
        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen({
                    fragmentManager
                        ?.beginTransaction()
                        ?.add(R.id.content, BlueScreen().newInstance(it))
                        ?.commit()
                })
            }
        }
    }

    @Composable
    private fun MainScreen(
        onClickCard: (index: Int) -> Unit,
        viewModel: MainViewModel = viewModel()
    ){
        val cardList by viewModel.cardList.collectAsState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding())
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GenerateItemsList(cardList.size, onClickCard)
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                BottomPanel(viewModel)
            }

        }
    }

    @Composable
    private fun BottomPanel(
        viewModel: MainViewModel = viewModel()
    ){
        Row{
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    viewModel.addCard(viewModel.cardList.value.size)
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

    @Composable
    private fun GenerateItemsList(
        currentCardList: Int,
        onClickCard: (index: Int) -> Unit
    ){
        val configuration = LocalConfiguration.current
        val countGridCells: Int = if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            3
        else
            4

        LazyVerticalGrid(
            modifier = Modifier.padding(5.dp),
            columns = GridCells.Fixed(countGridCells)
        ) {
            items(currentCardList){
                BoxItem(it, onClickCard)
            }
        }
    }

    @Composable
    private fun BoxItem(
        indexItem: Int,
        onClickCard: (index: Int) -> Unit
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
                .background(colorsEven)
                .clickable {
                    onClickCard(indexItem)
                },
            contentAlignment = Alignment.Center,

        ){
            Text(
                text = "$indexItem",
                fontSize = 26.sp,
                color = Color.White,
            )
        }
    }
}