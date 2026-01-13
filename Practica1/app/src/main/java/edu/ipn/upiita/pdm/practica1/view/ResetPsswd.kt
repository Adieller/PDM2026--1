package edu.ipn.upiita.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.ipn.upiita.pdm.practica1.R
import edu.ipn.upiita.pdm.practica1.databinding.ResetPasswordBinding
import edu.ipn.upiita.pdm.practica1.databinding.ResetPsswdBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

class ResetPsswd : AppCompatActivity() {
    //Se declara la variable binding para utilizar el activity binding
    private lateinit var binding: ResetPsswdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Se infla la clase de binding no se que significa exactamente pero es necesario para que funcione
        binding = ResetPsswdBinding.inflate(layoutInflater)
        //Se establece el contenido de la vista usando la raiz del binding si no estoy mal aqui es dond se pasan los elementos de la vista para acceder de forma m,as drecta
        setContentView(binding.root)

        enableEdgeToEdge()

        val email = intent.getStringExtra("email_key").toString().lowercase()

        binding.buttonConfirmarNuevaContra.setOnClickListener {
            if(!UserProvider.isValidPassword(binding.newpsw.text.toString().trim())) {
                Toast.makeText(this, "La conttraseña debe contener al menos 8 caracteres, un numero, una mayuscula y un signo especial", Toast.LENGTH_SHORT).show()
            } else if(UserProvider.isRepeatingPassword(email,binding.newpsw.text.toString().trim())){
                    Toast.makeText(this, "No puedes usar la misma contraseña que ya tienes. Elige una nueva.", Toast.LENGTH_SHORT).show()
                    }
                else if(binding.newpsw.text.toString().trim()==binding.newpswConfirm.text.toString().trim()){
                UserProvider.updatePassword(this, email, binding.newpsw.text.toString().trim())
                Toast.makeText(this, "Se cambio la contraseña exitosamente", Toast.LENGTH_SHORT).show()
                // 1. Crear la intención para ir a SegundaActivity
                val intent = Intent(this, LoginActivity::class.java)

                // 2. Iniciar la nueva Activity
                startActivity(intent)
                finish()

                }else{
                Toast.makeText(this, "ERROR: No coinciden las contraseñas", Toast.LENGTH_SHORT).show()
            }
        }

        binding.textViewBackToLogin.setOnClickListener {
            // 1. Crear la intención para ir de regreso al inicio
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}