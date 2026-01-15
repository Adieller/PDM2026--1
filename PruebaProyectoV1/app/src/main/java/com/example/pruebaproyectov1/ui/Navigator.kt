package com.example.pruebaproyectov1.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.pruebaproyectov1.data.database.AppDataBase
import com.example.pruebaproyectov1.data.repository.TaskRepository
import com.example.pruebaproyectov1.data.repository.UserRepository
import com.example.pruebaproyectov1.ui.screens.FormRegScreen
import com.example.pruebaproyectov1.ui.screens.HomeScreen
import com.example.pruebaproyectov1.ui.screens.LoginScreen
import com.example.pruebaproyectov1.ui.screens.RecoveryScreen
import com.example.pruebaproyectov1.viewmodel.FormRegViewModel
import com.example.pruebaproyectov1.viewmodel.LoginViewModel
import com.example.pruebaproyectov1.viewmodel.LoginViewModelFactory
import com.example.pruebaproyectov1.viewmodel.TaskViewModel
import com.example.pruebaproyectov1.viewmodel.UserViewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory

@Composable
fun Navigator(userRepository: UserRepository) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            val viewModel: LoginViewModel = viewModel(
                factory = LoginViewModelFactory(userRepository)
            )
            LoginScreen(
                navController,
                viewModel
            )
        }
        composable("recovery") { RecoveryScreen(navController) }
        composable("registro") {
            val viewModel: FormRegViewModel = viewModel(

                factory = UserViewModelFactory(userRepository)
            )

            FormRegScreen(
                navController,
                viewModel,
                onBack = {
                    // Cuando el registro se completa o se presiona "atrás",
                    // volvemos a la pantalla anterior en la pila de navegación.
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = "home/{userId}/{userName}",
            arguments = listOf(
                navArgument("userId") {
                    type = NavType.IntType
                    defaultValue = 0L // Valor por defecto por seguridad
                },
                navArgument("userName") {
                    type = NavType.StringType
                    defaultValue = "" // Valor por defecto
                    // nullable = true // Si permites nulos
                }
            )
        ) { backStackEntry ->
        val userId = backStackEntry.arguments?.getInt("userId") ?: 0
        val userName = backStackEntry.arguments?.getString("userName") ?: ""

        // Instancia el ViewModel (o úsalo si ya lo tienes inyectado con Hilt/Koin/Factory)
        val context = LocalContext.current
        val database = AppDataBase.getDatabase(context)
        val repository = TaskRepository(database.taskDao())
            val taskViewModel: TaskViewModel = viewModel(
                factory = viewModelFactory {
                    initializer {
                        TaskViewModel(repository)
                    }
                }
            )

        HomeScreen(navController, taskViewModel, userId.toLong(), userName)
        }

    }
}