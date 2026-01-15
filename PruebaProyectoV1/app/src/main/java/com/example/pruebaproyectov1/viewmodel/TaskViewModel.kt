package com.example.pruebaproyectov1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pruebaproyectov1.data.repository.TaskRepository
import com.example.pruebaproyectov1.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TaskViewModel (private val repository: TaskRepository) : ViewModel(){
    // Estado para la lista de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    // Guardamos el ID del usuario actual
    private var currentUserId: Long? = null

    // 1. INICIALIZAR: Llamar a esto cuando el usuario entra a la pantalla de tareas (HomeScreen)
    fun setUserId(userId: Long) {
        currentUserId = userId
        loadTasks()
    }

    private fun loadTasks() {
        currentUserId?.let { id ->
            viewModelScope.launch {
                repository.getTasks(id).collect { taskList ->
                    _tasks.value = taskList
                }
            }
        }
    }

    // 2. AGREGAR
    fun addTask(title: String) {
        currentUserId?.let { userId ->
            val newTask = Task(title = title, userOwnerId = userId)
            viewModelScope.launch {
                repository.addTask(newTask)
                // No hace falta recargar manualmente, el Flow lo hará solo
            }
        }
    }

    // 3. MARCAR COMO COMPLETADA / PENDIENTE
    fun toggleTaskCompletion(task: Task) {
        viewModelScope.launch {
            // Creamos una copia con el valor isDone invertido
            val updatedTask = task.copy(isDone = !task.isDone)
            repository.updateTask(updatedTask)
        }
    }

    // 4. EDITAR TEXTO
    fun updateTaskTitle(task: Task, newTitle: String) {
        viewModelScope.launch {
            val updatedTask = task.copy(title = newTitle)
            repository.updateTask(updatedTask)
        }
    }

    // 5. ELIMINAR
    fun deleteTask(task: Task) {
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }
}