package com.example.vk_kotlin_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vk_kotlin_learning.viewmodels.MainViewModel

class FailedLoadingScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                DetailCardScreen()
            }
        }
    }

    companion object

    fun newInstance(): FailedLoadingScreen {
        val fragment = FailedLoadingScreen()
        return fragment
    }

    @Composable
    fun DetailCardScreen(
        viewModel: MainViewModel = viewModel()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.35f)
                .background(Color.Black)
                .safeContentPadding(),
            contentAlignment = Alignment.Center
        ) {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            )
            {
                LaunchedEffect(viewModel.loading, viewModel.failedRequest) {
                    if (!viewModel.loading.value && !viewModel.failedRequest.value) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }

                Image(
                    painter = painterResource(R.drawable.loading_retry),
                    contentDescription = "loading_retry",
                    modifier = Modifier
                        .size(100.dp)
                        .clickable {
                            viewModel.loadingGifs()
                        },
                )

                Text(
                    text = "Failed Loading",
                    fontSize = 18.sp,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                )
            }

        }
    }
}
