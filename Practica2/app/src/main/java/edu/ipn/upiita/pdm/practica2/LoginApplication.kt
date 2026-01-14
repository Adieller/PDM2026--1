package edu.ipn.upiita.pdm.practica2

import android.app.Application
import edu.ipn.upiita.pdm.practica2.data.AppDataBase
import edu.ipn.upiita.pdm.practica2.data.UserRepository

/**
 * Clase Application personalizada para la aplicación.
 * Se utiliza para crear y mantener instancias únicas (Singleton) de la base de datos y el repositorio
 * que estarán disponibles durante todo el ciclo de vida de la aplicación.
 */

class LoginApplication : Application(){

    /**
     * `lazy` es un delegado de propiedad de Kotlin que asegura que la inicialización
     * de la base de datos ocurra solo una vez, la primera vez que se accede a `database`.
     * Es seguro para hilos (thread-safe) por defecto.
     */
    val database: AppDataBase by lazy { AppDataBase.getDatabase(this) }

    /**
     * De manera similar, el repositorio se inicializa de forma perezosa, utilizando la instancia
     * de la base de datos para obtener el `userDao`.
     */
    val repository: UserRepository by lazy { UserRepository(database.userDao()) }

}