package edu.ipn.upiita.pdm.practica1.model

import android.content.Context
import java.io.File
import java.security.MessageDigest
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserProvider {
    companion object {

        lateinit var listU: List<UserModel>

        fun copyJsonToInternal(context: Context) {
            val file = File(context.filesDir, "users.json")
            if (!file.exists()) {
                context.assets.open("users.json").use { input ->
                    file.outputStream().use { output ->
                        input.copyTo(output)
                    }
                }
            }
        }


        // Load users from JSON
        fun isValidEmail(email: String): Boolean {
            val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            return emailRegex.matches(email)
        }

        fun getAnswerByEmail(email: String): String? {
            listU.forEach { user ->
                if (user.email == email) {
                    return user.respuesta
                }
            }
            return null
        }

        fun getUsernameByEmail(email: String): String? {
            listU.forEach { user ->
                if (user.email == email) {
                    return user.name
                }
            }
            return null
        }

        fun readUsersFromAssets(context: Context) {
            try {
                val file = File(context.filesDir, "users.json")
                if (!file.exists()) copyJsonToInternal(context)

                val json = file.bufferedReader().use { it.readText() }
                val gson = Gson()
                val userType = object : TypeToken<List<UserModel>>() {}.type
                listU = gson.fromJson(json, userType)
            } catch (e: Exception) {
                e.printStackTrace()
                listU = emptyList()
            }
        }

        fun updatePassword(context: Context, email: String, newPassword: String) {
            listU = listU.map { user ->
                if (user.email == email)
                    user.copy(passwd = sha256(newPassword))
                else
                    user
            }
            val file = File(context.filesDir, "users.json")
            val gson = Gson()
            file.writeText(gson.toJson(listU))
        }


        fun getQuestionByEmail(email: String): String? {
            listU.forEach { user ->
                if (user.email == email) {
                    return user.pregunta
                }
            }
            return null
        }

        // SHA-256 hash function
        fun sha256(input: String): String {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(input.toByteArray(Charsets.UTF_8))
            return hashBytes.joinToString("") { "%02x".format(it) }
        }

        // Validate login
        fun validateEmailPw(email: String, pw: String): Boolean {
            val pwHash = sha256(pw)  // hash only the input
            listU.forEach { user ->
                if (user.email == email && user.passwd == pwHash) {
                    return true
                }
            }
            return false
        }

        fun emailExists(email: String): Boolean {
            listU.forEach { user ->
                if (user.email == email) {
                    return true
                }
            }
            return false
        }
        //Valida que la contraseña que se escriba cumpla con las condiciones solicitadas de al menos 1 numero al menos una mayuzcula al menos un caracter especial y que tenga al menos 8 digitos
        fun isValidPassword(password: String): Boolean{
            val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[_.#$?]).{8,}$")
            return passwordRegex.matches(password)
        }

        fun isRepeatingPassword(email: String, currentPassword: String): Boolean {
            listU.forEach { user ->
                if (user.email == email) {
                    if (sha256(currentPassword) == user.passwd) {
                        return true;
                    }
                }

            }
            return false;
        }
    }
}
