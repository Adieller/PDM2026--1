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
import edu.ipn.upiita.pdm.practica1.databinding.RecoveryBinding
import edu.ipn.upiita.pdm.practica1.databinding.ResetPasswordBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

class ResetPassword : AppCompatActivity() {
    //Se declara la variable binding para utilizar el activity binding
    private lateinit var binding: ResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se infla la clase de binding no se que significa exactamente pero es necesario para que funcione
        binding = ResetPasswordBinding.inflate(layoutInflater)
        //Se establece el contenido de la vista usando la raiz del binding si no estoy mal aqui es dond se pasan los elementos de la vista para acceder de forma m,as drecta
        setContentView(binding.root)

        enableEdgeToEdge()
        //Creamos una variable donde guardamos el correo que se envio de la pantalla anterior y con ello poder trabajar con ese correo
        val email = intent.getStringExtra("email_key").toString().lowercase()
        //Asignamos la pregunta de seguridad al texto para que se muestre en pantalla
        binding.txtPreguntaSeguridad.text = UserProvider.getQuestionByEmail(email)

        binding.buttonValidarRespuesta.setOnClickListener {
            val answer = UserProvider.getAnswerByEmail(email)
            if(!(answer==binding.txtRespuesta.text.toString().trim().lowercase())){
                Toast.makeText(this, "Respuesta incorrecta", Toast.LENGTH_SHORT).show()
            }else{
                // 1. Crear la intención para ir a SegundaActivity
                val intent = Intent(this, ResetPsswd::class.java)
                intent.putExtra("email_key", email)
                // 2. Iniciar la nueva Activity
                startActivity(intent)
                finish()
            }
        }
        binding.textViewBackToLogin.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, LoginActivity::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }
    }
}