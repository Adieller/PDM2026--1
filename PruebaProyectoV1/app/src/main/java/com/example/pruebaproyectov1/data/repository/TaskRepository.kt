package com.example.pruebaproyectov1.data.repository

import com.example.pruebaproyectov1.data.database.TaskDao
import com.example.pruebaproyectov1.model.Task
import kotlinx.coroutines.flow.Flow

class TaskRepository(private val taskDao: TaskDao) {
    // Retorna un flujo de datos (Flow) que se actualiza automáticamente si la DB cambia
    fun getTasks(userId: Long): Flow<List<Task>> = taskDao.getTasksByUserId(userId)

    suspend fun addTask(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }
}