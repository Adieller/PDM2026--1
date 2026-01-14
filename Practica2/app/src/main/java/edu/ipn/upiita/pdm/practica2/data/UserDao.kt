package edu.ipn.upiita.pdm.practica2.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ipn.upiita.pdm.practica2.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    /**
     * Inserta un nuevo usuario en la tabla.
     *
     * @Insert - Anotación para métodos que insertan datos.
     *   `onConflict = OnConflictStrategy.IGNORE` indica que si se intenta insertar un
     *   usuario con una clave primaria que ya existe, la operación se ignorará.
     */

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: User)

    /**
     * Obtiene todos los usuarios de la tabla
     *
     * @Query - Anotación para definir consultas SQL personalizadas.
     *
     *   El resultado se envuelve en un `Flow`, lo que permite que la UI se actualice
     *   automáticamente cuando los datos cambien.
     */

    @Query("SELECT *FROM users")
    fun getAllUsers(): Flow<List<User>>

    // Busca un usuario donde el email coincida.
    // Devuelve null si no existe.
    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): User?

}