package edu.ipn.upiita.pdm.practica1.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import edu.ipn.upiita.pdm.practica1.R
import edu.ipn.upiita.pdm.practica1.databinding.ActivityHomeBinding
import edu.ipn.upiita.pdm.practica1.model.UserProvider

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    // 1. DECLARAMOS LA VARIABLE AQUÍ ARRIBA (Global para esta clase)
    private lateinit var currentEmail: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        // 2. ASIGNAMOS EL VALOR AQUÍ
        // Usamos una protección: si viene nulo, usamos un texto vacío para que no truene
        currentEmail = intent.getStringExtra("email_key")?.toString()?.lowercase() ?: ""

        binding.btnGoToProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            // Usamos la variable global
            intent.putExtra("email_key", currentEmail)
            startActivity(intent)
        }

        setSupportActionBar(binding.toolbar)
        // Usamos la variable global
        binding.tvUserNameDisplay.text = UserProvider.getUsernameByEmail(currentEmail)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_password -> {
                val intentCambio = Intent(this, ResetPsswd::class.java)

                // 3. USAMOS LA VARIABLE QUE YA GUARDAMOS (Segura)
                intentCambio.putExtra("email_key", currentEmail)

                startActivity(intentCambio)

                finish()
                true
            }
            R.id.action_logout -> {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}