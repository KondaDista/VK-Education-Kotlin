package com.example.vk_kotlin_learning

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vk_kotlin_learning.giphy_api.GiphyController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null)
            return
        supportFragmentManager
            .beginTransaction()
            .add(R.id.content, MainScreenFragment())
            .commit()
    }
}

