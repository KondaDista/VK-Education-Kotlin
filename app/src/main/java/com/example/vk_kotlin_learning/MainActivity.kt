package com.example.vk_kotlin_learning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }

    @Preview(showSystemUi = true)
    @Composable
    private fun MainContent(){
        val countItems = remember { mutableIntStateOf(0) }
        MainScreen(countItems)
    }

    @Composable
    private fun MainScreen(
        countItems: MutableIntState,
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
                GenerateItemsList(countItems.intValue)
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                BottomPanel(countItems)
            }

        }
    }

    @Composable
    private fun BottomPanel(
        countItems: MutableIntState,
    ){
        Text(
            text = "Count Items: ${countItems.intValue}",
            modifier = Modifier.fillMaxWidth(),
            fontSize = 24.sp,
            color = Color.Yellow,
            textAlign = TextAlign.Center
        )
        Row{

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {

                },
                enabled = false,
                colors = ButtonColors(Color.Red, Color.White, Color.Gray, Color.Black)
            ){
                Text(
                    text = "Remove item",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    countItems.intValue += 1
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
        countItems: Int,
    ){

        val data = List(countItems){ it}
        LazyVerticalGrid(
            modifier = Modifier.padding(5.dp, 5.dp, 5.dp),
            columns = GridCells.Fixed(3)
        ) {
            items(data.size){
                BoxItem(it)
            }
        }
    }


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
                .size(120.dp)
                .clip(RoundedCornerShape(16.dp))
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

