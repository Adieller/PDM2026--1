package com.example.pruebaproyectov1.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pruebaproyectov1.data.database.TaskDao
import com.example.pruebaproyectov1.data.database.UserDao
import com.example.pruebaproyectov1.model.Task
import com.example.pruebaproyectov1.model.User

@Database(entities = [User::class, Task::class], version = 1, exportSchema = false)

abstract class AppDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao

    // 3. Agregamos el DAO para las tareas
    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "app_database" // Recomendación: Evita usar "users" si ahora guarda más cosas, "app_database" es más genérico.
                )
                    // Opcional: Si estás en desarrollo y no quieres escribir migraciones complejas,
                    // esto borra la base de datos vieja y crea la nueva cuando cambia la versión.
                    // .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}