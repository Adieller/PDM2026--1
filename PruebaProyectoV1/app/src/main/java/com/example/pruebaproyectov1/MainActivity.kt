package com.example.pruebaproyectov1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.pruebaproyectov1.ui.Navigator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = application as MyApp
        val userRepository = appContainer.repository
        setContent {
            Navigator(userRepository = userRepository)
        }
    }
}