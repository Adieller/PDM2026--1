package edu.ipn.upiita.pdm.practica2.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.ipn.upiita.pdm.practica2.model.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return edu.ipn.upiita.pdm.practica2.data.AppDataBase.Companion.INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "users" // El nombre del archivo de la base de datos.
                ).build()
                edu.ipn.upiita.pdm.practica2.data.AppDataBase.Companion.INSTANCE = instance
                instance
            }
        }
    }
}