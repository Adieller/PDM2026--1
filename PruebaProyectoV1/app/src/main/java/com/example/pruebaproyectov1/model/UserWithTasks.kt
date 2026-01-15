package com.example.pruebaproyectov1.model

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithTasks(
    @Embedded val user: User,

    @Relation(
        parentColumn = "userId",      // ID en la clase User
        entityColumn = "userOwnerId"  // ID en la clase Task
    )
    val tasks: List<Task>
)
