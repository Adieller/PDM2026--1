package com.example.pruebaproyectov1

import android.app.Application
import com.example.pruebaproyectov1.data.database.AppDataBase
import com.example.pruebaproyectov1.data.repository.UserRepository

class MyApp : Application(){
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