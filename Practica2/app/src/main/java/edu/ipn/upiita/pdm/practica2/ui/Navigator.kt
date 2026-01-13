package edu.ipn.upiita.pdm.practica2.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigator() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") { LoginScreen(navController) }
    }
}