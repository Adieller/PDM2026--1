package com.example.pruebaproyectov1.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    "tasks",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userOwnerId"],
            onDelete = ForeignKey.CASCADE // Si borras al usuario, se borran sus tareas
        )
    ]
)

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Clave primaria, se autogenera

    //Datos que tienen las tareas
    val title: String,
    val isDone: Boolean = false,
    val userOwnerId: Long
)
