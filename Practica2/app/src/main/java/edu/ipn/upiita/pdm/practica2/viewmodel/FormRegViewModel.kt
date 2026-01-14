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
import java.util.regex.Pattern

class FormRegViewModel(private val repository: UserRepository) : ViewModel() {

    var usuario by mutableStateOf("")
    var contrasena by mutableStateOf("")
    var confirmarContrasena by mutableStateOf("")
    var email by mutableStateOf("")

    var usuarioError  by mutableStateOf<String?>(null)
    var contrasenaError  by mutableStateOf<String?>(null)
    var confirmarContrasenaError  by mutableStateOf<String?>(null)
    var emailError  by mutableStateOf<String?>(null)

    var registroExitoso by mutableStateOf(false)

    fun validarCampos(): Boolean {
        var isValid = true

        usuarioError = if (usuario.isBlank()) {
            isValid = false
            "El nombre es obligatorio"
        } else null

        emailError = if (!isEmailValido(email)) {
            isValid = false
            "Correo no válido"
        } else null

        contrasenaError = if (!isValidPassword(contrasena)) {
            isValid = false
            "La contraseña debe tener al menos 8 caracteres,un numero, una mayuscula y un signo especial"
        } else null

        confirmarContrasenaError = if (confirmarContrasena != contrasena) {
            isValid = false
            "Las contraseñas no coinciden"
        } else null

        registroExitoso = isValid
        return isValid
    }

    private fun isEmailValido(email: String): Boolean {
        val pattern = Pattern.compile(
            "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        )
        return pattern.matcher(email).matches()
    }
    private fun isValidPassword(password: String): Boolean {
        val pattern = Pattern.compile(
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[_.#$?]).{8,}$"
        )
        return pattern.matcher(password).matches()
    }


    /**
     * Una lista de usuarios expuesta como `StateFlow`.
     *
     * `repository.allUsers` es un `Flow` que emite la lista de usuarios desde la base de datos.
     *
     * `.stateIn(...)` convierte el `Flow` frío en un `StateFlow` caliente. Esto significa que
     * el flujo se mantiene activo mientras el `viewModelScope` esté activo, y el último valor
     * emitido se guarda y se emite a los nuevos colectores. Es ideal para la UI.
     *
     * - `viewModelScope`: El `CoroutineScope` vinculado al ciclo de vida del ViewModel.
     * - `SharingStarted.WhileSubscribed(5000)`: Inicia el flujo cuando hay un suscriptor
     *   y lo detiene 5 segundos después de que el último suscriptor se vaya. Esto ahorra recursos.
     * - `emptyList()`: El valor inicial del `StateFlow`.
     */
    val allUsers: StateFlow<List<User>> = repository.allUsers.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    /**
     * Lanza una corutina en el `viewModelScope` para insertar un usuario.
     * Se usa `viewModelScope.launch` para ejecutar la función `suspend` `repository.insert`
     * en un hilo de fondo, sin bloquear la UI.
     *
     * @param user El usuario a insertar.
     */
    fun addUser(user: User) {
        viewModelScope.launch {
            repository.insert(user)
        }
    }

}

/**
 * Factory para crear instancias de `UserViewModel`.
 *
 * Es necesaria porque `UserViewModel` tiene un constructor con parámetros (`UserRepository`),
 * y no podemos instanciarlo directamente.
 */
class UserViewModelFactory(private val repository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FormRegViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FormRegViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}