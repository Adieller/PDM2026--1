package com.example.pruebaproyectov1.data.repository

import com.example.pruebaproyectov1.data.database.UserDao
import com.example.pruebaproyectov1.model.User
import kotlinx.coroutines.flow.Flow
import kotlin.text.insert

class UserRepository(private val userDao: UserDao) {
    val allUsers: Flow<List<User>> = userDao.getAllUsers()

    /**
     * Una función `suspend` para insertar un nuevo usuario.
     * Las funciones `suspend` pueden ser pausadas y reanudadas, lo que las hace
     * ideales para operaciones de larga duración como el acceso a la base de datos,
     * sin bloquear el hilo principal.
     *
     * @param user El usuario a insertar.
     */
    suspend fun insert(user: User) {
        userDao.insert(user)
    }

    //Funcion para conectar con el dao y devolver el usuario si existe en la base de datos o null si no existe
    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }

    //Funcion para conectar con el dao y devolver el email si existe en la base de datos o null si no existe
    suspend fun getEmailByEmail(email: String): User?{
        return userDao.getEmailByEmail(email)
    }

    suspend fun getUserByEmail(email: String): User?{
        return userDao.getUserByEmail(email)
    }

    suspend fun updatePassword(email: String, newPassword: String){
        return userDao.updatePassword(email, newPassword)
    }
}