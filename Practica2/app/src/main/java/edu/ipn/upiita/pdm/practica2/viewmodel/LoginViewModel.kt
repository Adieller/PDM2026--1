package edu.ipn.upiita.pdm.practica2.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.ipn.upiita.pdm.practica2.data.UserRepository
import edu.ipn.upiita.pdm.practica2.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {

    var usuario by mutableStateOf("")
    var contrasena by mutableStateOf("")

    var usuarioError  by mutableStateOf<String?>(null)
    var contrasenaError  by mutableStateOf<String?>(null)

    // Función modificada para Room
    fun validarLogin(onLoginSuccess: (String) -> Unit) {
        // 1. Validaciones básicas de campos vacíos
        if (usuario.isBlank() || contrasena.isBlank()) {
            if (usuario.isBlank()) usuarioError = "Requerido"
            if (contrasena.isBlank()) contrasenaError = "Requerido"
            return
        }

        // 2. Lanzamos la corrutina para hablar con la Base de Datos
        viewModelScope.launch {
            // Ejecutamos la consulta en segundo plano
            val usuarioEncontrado = repository.getUserByUsername(usuario)

            // 3. Validamos el resultado
            if (usuarioEncontrado != null) {
                // El usuario existe, ahora comparamos la contraseña
                if (usuarioEncontrado.password == contrasena) {
                    // ¡Éxito! Limpiamos errores y navegamos
                    usuarioError = null
                    contrasenaError = null
                    onLoginSuccess(usuarioEncontrado.username)
                } else {
                    contrasenaError = "Contraseña incorrecta"
                }
            } else {
                // El usuario no existe en la BD
                usuarioError = "Usuario no registrado"
            }
        }
    }












    val allUsers: StateFlow<List<User>> = repository.allUsers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )



}

class LoginViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return LoginViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}