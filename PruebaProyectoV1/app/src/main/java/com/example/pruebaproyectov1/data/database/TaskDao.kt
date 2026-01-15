package com.example.pruebaproyectov1.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.pruebaproyectov1.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    // LISTAR: Obtiene solo las tareas del usuario logueado
    @Query("SELECT * FROM tasks WHERE userOwnerId = :userId ORDER BY id DESC")
    fun getTasksByUserId(userId: Long): Flow<List<Task>>

    // AGREGAR: Inserta una nueva tarea
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    // EDITAR / COMPLETAR: Actualiza una tarea existente (sirve para cambiar título o marcar isDone)
    @Update
    suspend fun updateTask(task: Task)

    // ELIMINAR: Borra una tarea
    @Delete
    suspend fun deleteTask(task: Task)
}