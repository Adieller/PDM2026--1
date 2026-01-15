package com.example.pruebaproyectov1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Clave primaria, se autogenera.
    //Datos de la tabla
    val username: String,
    val password: String,
    val email: String,
    val securityQuestion: String,
    val securityAnswer: String
)
