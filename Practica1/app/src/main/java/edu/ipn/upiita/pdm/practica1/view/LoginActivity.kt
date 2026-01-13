package edu.ipn.upiita.pdm.practica1.view
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.ipn.upiita.pdm.practica1.R
import edu.ipn.upiita.pdm.practica1.databinding.ActivityLoginBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

class LoginActivity : AppCompatActivity() {
    //Se declara la variable binding para utilizar el activity binding
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Se infla la clase de binding no se que significa exactamente pero es necesario para que funcione
        binding = ActivityLoginBinding.inflate(layoutInflater)
        //Se establece el contenido de la vista usando la raiz del binding si no estoy mal aqui es dond se pasan los elementos de la vista para acceder de forma m,as drecta
        setContentView(binding.root)


        enableEdgeToEdge()
    //Una vez que inicie la app se cargan directamente los datos del json y se copian en listU para tenerlos en memoria y poder trabajar directamente con los datos
        UserProvider.copyJsonToInternal(this)
        UserProvider.readUsersFromAssets(this)

        //este pequeño bloque es solo academico y ayuda debuggear en consola que se hayan caargado los datos coprrectamente en la memoria ram para esta practioca es meramenmte academico
        UserProvider.listU.forEach {
            Log.d("LoginDebug", "User: ${it.email}, Pass: ${it.passwd}")
        }
        //Aqui utilizamos directamente con el binding el id del boton para hacer directamenta la navegacion a la patalla de olvide mi contraseña

        binding.forgotPsswrdButton.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email = binding.inputEmail.text.toString().trim().lowercase()
            val password = binding.psswordInput.text.toString().trim()
            if (!UserProvider.isValidEmail(email)) {
                Toast.makeText(this, "Favor de Ingresar un correo valido!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            else{
                // Validate
                val isValid = UserProvider.validateEmailPw(email, password)

                if (isValid) {
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("email_key", email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Email o password invalidos!", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}