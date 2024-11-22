package com.example.vk_kotlin_learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val giphyController by lazy { GiphyController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, MainScreenFragment(giphyController))
            .commit()
    }
}

