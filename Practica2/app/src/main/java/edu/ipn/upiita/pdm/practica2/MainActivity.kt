package edu.ipn.upiita.pdm.practica2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import edu.ipn.upiita.pdm.practica2.ui.Navigator


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appContainer = application as LoginApplication
        val userRepository = appContainer.repository

        setContent {
            Navigator(userRepository = userRepository)
        }
    }
}