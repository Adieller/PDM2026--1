package edu.ipn.upiita.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import edu.ipn.upiita.pdm.practica1.databinding.RecoveryBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

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

        binding.textViewBackToLogin.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, LoginActivity::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }

        //Buscar el correo electronico valido y si es que existe en el registro para hacer el recuperar la contraseña
        binding.buttonRecuperarCuenta.setOnClickListener {
            val email = binding.txtfieldCorreo.text.toString().trim().lowercase()
            if (!UserProvider.isValidEmail(email)) {
                Toast.makeText(this, "Favor de Ingresar un correo valido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else if(!UserProvider.emailExists(email)){
                Toast.makeText(this,"Ese correo no se encuentra registrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }else{
                // 1. Crear la intención para ir a SegundaActivity
                val intent = Intent(this, ResetPassword::class.java)
                //Manda solo el email a la siguiente vista para que con ese email poder acceder a los demas recursos
                intent.putExtra("email_key", email)
                // 2. Iniciar la nueva Activity
                startActivity(intent)
                //Finaliza la activida actual una vez que pasa a la siguiente
                finish()

            }
        }

    }
}