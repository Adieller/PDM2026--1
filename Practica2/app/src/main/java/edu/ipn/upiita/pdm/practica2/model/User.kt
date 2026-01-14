package edu.ipn.upiita.pdm.practica2.model;
import androidx.room.Entity
import androidx.room.PrimaryKey
//Ponemos una entidad qque sera el nombre de nuestra tabla y creamos un data class aqui esta lo que estaa e la tabla y el modelo de usuario
@Entity(tableName = "users")
data class User(
        @PrimaryKey(autoGenerate = true)
        val id: Int = 0, // Clave primaria, se autogenera.
        //Datos de la tabla
        val username: String,
        val password: String,
        val email: String
)
