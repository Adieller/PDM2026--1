package edu.ipn.upiita.pdm.practica1.view
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import edu.ipn.upiita.pdm.practica1.R
import edu.ipn.upiita.pdm.practica1.databinding.ActivityLoginBinding

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

        //Aqui utilizamos directamente con el binding el id del boton para hacer directamenta la navegacion a la patalla de olvide mi contraseña

        binding.forgotPsswrdButton.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }
    }
}