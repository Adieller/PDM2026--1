package edu.ipn.upiita.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import edu.ipn.upiita.pdm.practica1.databinding.ActivityProfileBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

class ProfileActivity : AppCompatActivity() {
    //Se declara la variable binding para utilizar el activity binding
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        // Se infla la clase de binding no se que significa exactamente pero es necesario para que funcione
        binding = ActivityProfileBinding.inflate(layoutInflater)
        //Se establece el contenido de la vista usando la raiz del binding si no estoy mal aqui es dond se pasan los elementos de la vista para acceder de forma m,as drecta
        setContentView(binding.root)

        enableEdgeToEdge()
        val email = intent.getStringExtra("email_key").toString().lowercase()
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.tvUserNameValue.text = UserProvider.getUsernameByEmail(email)
        binding.tvUserEmailValue.text = email
        
    }
}