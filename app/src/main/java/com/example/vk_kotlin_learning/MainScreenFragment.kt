package com.example.vk_kotlin_learning

import android.content.res.Configuration
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.vk_kotlin_learning.viewmodels.MainViewModel

class MainScreenFragment() : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen()
            }
        }
    }

    private fun showFailedLoadingScreen() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, FailedLoadingScreen().newInstance())
            .addToBackStack(null)
            .commit()
    }

    @Composable
    private fun MainScreen(
    ) {
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
                GenerateItemsList()
            }
        }
    }


    @Composable
    private fun GenerateItemsList(
        viewModel: MainViewModel = viewModel()
    ) {
        val configuration = LocalConfiguration.current
        val gifsList = viewModel.gifsList.collectAsState()
        val loading by viewModel.loading
        val failedRequest by viewModel.failedRequest

        val countGridCells: Int =
            if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
                3
            else
                4

        if (failedRequest && gifsList.value.isEmpty()) {
            showFailedLoadingScreen()
        } else {
            LazyVerticalGrid(
                modifier = Modifier.padding(5.dp),
                columns = GridCells.Fixed(countGridCells)
            ) {
                itemsIndexed(gifsList.value) { index, gif ->
                    GifItem(gif)
                    if (index == gifsList.value.lastIndex && !loading) {
                        viewModel.loadingGifs()
                    }
                }
            }
        }
    }

    @Composable
    private fun GifItem(
        gifData: GifData,
    ) {
        var isLoading by remember { mutableStateOf(true) }
        var isFailedLoad by remember { mutableStateOf(false) }
        var isVisible by remember { mutableStateOf(true) }


        Box(
            modifier = Modifier
                .padding(2.dp)
                .clip(RoundedCornerShape(16.dp))
                .aspectRatio(gifData.images.original.width.toFloat() / gifData.images.original.height.toFloat())
                .background(Color.Black),
            contentAlignment = Alignment.BottomCenter,
        ) {

            AndroidView(
                factory = { context ->
                    ImageView(context).apply {
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                },
                modifier = Modifier.fillMaxSize(),
                update = { imageView ->
                    Glide.with(imageView.context)
                        .load(gifData.images.original.url)
                        .diskCacheStrategy(
                            DiskCacheStrategy.ALL
                        )
                        .listener(object : RequestListener<Drawable> {

                            override fun onResourceReady(
                                resource: Drawable,
                                model: Any,
                                target: Target<Drawable>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {
                                isLoading = false
                                return false
                            }

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>,
                                isFirstResource: Boolean
                            ): Boolean {
                                isLoading = false
                                isFailedLoad = true
                                isVisible = true
                                return false
                            }

                        })
                        .into(imageView)
                }
            )

            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.White
                )
            }

            if (isFailedLoad) {
                if (isVisible) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                isVisible = false
                                isLoading = true
                            },
                        contentAlignment = Alignment.BottomCenter,
                    ) {
                        Image(
                            painter = painterResource(R.drawable.loading_retry),
                            contentDescription = "loading_retry",
                            modifier = Modifier
                                .align(Alignment.Center)
                                .size(35.dp)
                        )
                    }
                }
            }
        }
    }
}