package com.example.yahtzeegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel =
            ViewModelProvider(this)[YahtzeeViewModel::class.java]

        setContent {
            YahtzeeScreen(
                viewModel = viewModel
            )
        }
    }
}