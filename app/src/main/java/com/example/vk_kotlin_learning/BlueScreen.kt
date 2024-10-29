package com.example.vk_kotlin_learning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class BlueScreen: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val indexCard:Int = arguments?.getInt("IndexCard") ?: return ComposeView(requireContext())

        return ComposeView(requireContext()).apply {
            setContent {
                DetailCardScreen(indexCard)
            }
        }
    }

    companion object

    fun newInstance(indexCard: Int): BlueScreen{
        val args = Bundle()
        args.putInt("IndexCard", indexCard)
        val fragment = BlueScreen()
        fragment.arguments = args
        return fragment
    }
}
