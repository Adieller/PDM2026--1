package edu.ipn.upiita.pdm.practica2.data

import edu.ipn.upiita.pdm.practica2.model.User
import kotlinx.coroutines.flow.Flow

/**
 * Un repositorio que gestiona el acceso a los datos de usuario.
 * Actúa como intermediario entre el ViewModel y la fuente de datos (la base de datos Room).
 *
 * @param userDao El DAO para acceder a los datos del usuario.
 */

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

    suspend fun getUserByUsername(username: String): User? {
        return userDao.getUserByUsername(username)
    }


}