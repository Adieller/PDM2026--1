package edu.ipn.upiita.pdm.practica2.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import edu.ipn.upiita.pdm.practica2.data.UserRepository
import edu.ipn.upiita.pdm.practica2.viewmodel.FormRegViewModel
import edu.ipn.upiita.pdm.practica2.viewmodel.UserViewModelFactory

@Composable
fun Navigator(userRepository: UserRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("recovery") { RecoveryScreen(navController) }
        composable("registro") {
            val viewModel: FormRegViewModel = viewModel(
                factory = UserViewModelFactory(userRepository)
            )

            FormRegScreen(navController, viewModel)
        }
        composable("home") { HomeScreen(navController) }

    }
}