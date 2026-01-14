package edu.ipn.upiita.pdm.practica2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.ipn.upiita.pdm.practica2.data.UserRepository
import edu.ipn.upiita.pdm.practica2.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.text.insert

class FormRegViewModel(private val repository: UserRepository) : ViewModel() {
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