package com.example.vk_kotlin_learning

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainScreenFragment(giphyController: GiphyController) : Fragment() {

    val giphyCon = giphyController

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
    ) {
        var gifList by rememberSaveable { mutableStateOf<List<GifData>?>(null) }
        var dialogueShown by rememberSaveable { mutableStateOf(false) }
        var isLoading by rememberSaveable { mutableStateOf(false) }
        var hasError by rememberSaveable { mutableStateOf(false) }
        var buttonUsed by rememberSaveable { mutableStateOf(false) }
        var lastEnteredQuery by rememberSaveable { mutableStateOf("") }
        val coroutineScope = rememberCoroutineScope()
        val handler = CoroutineExceptionHandler { _, exception ->
            run {
                isLoading = false
                hasError = true
            }
        }

        updateTrendingGifs(
            coroutineScope,
            giphyCon,
            handler,
            {gifList = it},
            {isLoading = it},
            {hasError = it},
            {buttonUsed = it}
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding()
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                GenerateItemsList(gifList, onClickCard)
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
            ) {
                BottomPanel(coroutineScope, handler, viewModel)
            }

        }
    }

    @Suppress("NonSkippableComposable")
    @Composable
    private fun BottomPanel(
        coroutineScope: CoroutineScope,
        handler: CoroutineExceptionHandler,
        viewModel: MainViewModel = viewModel()
    ){

        var gifList by rememberSaveable { mutableStateOf<List<GifData>?>(null) }
        var isLoading by rememberSaveable { mutableStateOf(false) }
        var hasError by rememberSaveable { mutableStateOf(false) }
        var buttonUsed by rememberSaveable { mutableStateOf(false) }

        Row{
            Button(
                modifier = Modifier
                    .weight(1f)
                    .padding(3.dp),
                onClick = {
                    coroutineScope.launch(handler) {}
                    updateTrendingGifs(
                        coroutineScope,
                        giphyCon,
                        handler,
                        {gifList = it},
                        {isLoading = it},
                        {hasError = it},
                        {buttonUsed = it}
                    )
                    viewModel.addGifs(gifList ?: emptyList())
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
    private fun GenerateItemsList(
        gifList: List<GifData>?,
        onClickCard: (index: Int) -> Unit,
        viewModel: MainViewModel = viewModel()
    ) {
        val configuration = LocalConfiguration.current
        //val gifList = viewModel.cardList.collectAsState().value

        val countGridCells: Int =
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                3
            else
                4

        LazyVerticalGrid(
            modifier = Modifier.padding(5.dp),
            columns = GridCells.Fixed(countGridCells)
        ) {
            items(gifList ?: emptyList()){ gifData ->
                GifItem(gifData, onClickCard)
            }
        }
    }

    @Composable
    private fun GifItem(
        gifData: GifData,
        onClickCard: (index: Int) -> Unit
    ) {
        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(gifData.images.original.width.toFloat() / gifData.images.original.height.toFloat())
                .background(Color.Black),
            /*.clickable {
                onClickCard(indexItem)
            },*/
            contentAlignment = Alignment.BottomCenter,
            ) {

            AndroidView(factory = { context ->
                ImageView(context).apply {
                    scaleType = ImageView.ScaleType.FIT_CENTER
                }
            }, modifier = Modifier.fillMaxSize(),
               update = { imageView ->
                    Glide.with(imageView.context).load(gifData.images.original.url)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                        .into(imageView)
                }
            )

/*            Text(
                text = "$gifData.title",
                fontSize = 26.sp,
                color = Color.White,
                textAlign = TextAlign.Center
            )*/
        }
    }

    private fun updateTrendingGifs(
        coroutineScope: CoroutineScope,
        giphyRepository: GiphyController,
        handler: CoroutineExceptionHandler,
        setGifList: (List<GifData>?) -> Unit,
        setLoading: (Boolean) -> Unit,
        setError: (Boolean) -> Unit,
        setButtonUsed: (Boolean) -> Unit
    ) {
        coroutineScope.launch(handler) {
            setButtonUsed(false)
            setLoading(true)
            setError(false)
            try {
                val response = giphyRepository.requestTrendingGif(20)
                setGifList(response?.data)
            } catch (e: Exception) {
                setError(true)
            } finally {
                setLoading(false)
            }
        }
    }
}