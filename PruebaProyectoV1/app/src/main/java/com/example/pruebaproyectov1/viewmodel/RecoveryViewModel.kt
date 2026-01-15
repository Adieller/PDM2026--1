package com.example.pruebaproyectov1.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.pruebaproyectov1.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.regex.Pattern

class RecoveryViewModel (private val repository: UserRepository) : ViewModel(){
    // Estados de los campos de texto
    var email by mutableStateOf("")
    var respuestaUsuario by mutableStateOf("")
    var nuevaContrasena by mutableStateOf("")
    var confirmarNuevaContrasena by mutableStateOf("")

    // Estado lógico de la pantalla
    // 0 = Ingresar Email, 1 = Responder Pregunta, 2 = Cambiar Password, 3 = Éxito
    var currentStep by mutableStateOf(0)

    // Datos recuperados de la BD
    var preguntaSeguridad by mutableStateOf("")
    private var respuestaCorrectaReal = "" // La guardamos privada para comparar

    // Mensajes de error
    var errorMessage by mutableStateOf<String?>(null)

    // PASO 1: Buscar el usuario
    fun buscarUsuario() {
        viewModelScope.launch {
            val user = repository.getUserByEmail(email)
            if (user != null) {
                preguntaSeguridad = user.securityQuestion
                respuestaCorrectaReal = user.securityAnswer
                errorMessage = null
                currentStep = 1 // Avanzamos a la siguiente fase
            } else {
                errorMessage = "El correo no está registrado."
            }
        }
    }

    // PASO 2: Validar la respuesta
    fun validarRespuesta() {
        if (respuestaUsuario.equals(respuestaCorrectaReal, ignoreCase = true)) {
            errorMessage = null
            currentStep = 2 // Avanzamos a cambiar contraseña
        } else {
            errorMessage = "Respuesta incorrecta."
        }
    }

    // PASO 3: Actualizar contraseña
    fun actualizarContrasena(onSuccess: () -> Unit) {
        if (nuevaContrasena.isBlank() || nuevaContrasena != confirmarNuevaContrasena) {
            errorMessage = "Las contraseñas no coinciden o están vacías."
            return
        }else if(!isValidPassword(nuevaContrasena)){
            errorMessage = "La contraseña debe tener al menos 8 caracteres, un número, una mayúscula y un signo especial "
            return
        }

        viewModelScope.launch {
            repository.updatePassword(email, nuevaContrasena)
            currentStep = 3
            onSuccess() // Callback para navegar al Login
        }
    }
    private fun isValidPassword(password: String): Boolean {
        val pattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[_.#$?]).{8,}$"
        )
        return pattern.matcher(password).matches()
    }
}

class RecoveryViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.pruebaproyectov1.viewmodel.RecoveryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecoveryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}