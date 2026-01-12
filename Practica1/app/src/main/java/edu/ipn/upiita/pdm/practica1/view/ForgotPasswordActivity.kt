package edu.ipn.upiita.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.ipn.upiita.pdm.practica1.R
import edu.ipn.upiita.pdm.practica1.databinding.ActivityLoginBinding
import edu.ipn.upiita.pdm.practica1.databinding.RecoveryBinding

class ForgotPasswordActivity : AppCompatActivity() {
    //Se declara la variable binding para utilizar el activity binding
    private lateinit var binding: RecoveryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se infla la clase de binding no se que significa exactamente pero es necesario para que funcione
        binding = RecoveryBinding.inflate(layoutInflater)
        //Se establece el contenido de la vista usando la raiz del binding si no estoy mal aqui es dond se pasan los elementos de la vista para acceder de forma m,as drecta
        setContentView(binding.root)

        enableEdgeToEdge()

        binding.buttonRecuperarCuenta.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, ResetPassword::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }

        binding.textViewBackToLogin.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, LoginActivity::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }
    }
}