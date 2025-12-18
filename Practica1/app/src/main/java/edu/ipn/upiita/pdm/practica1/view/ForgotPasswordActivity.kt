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

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recovery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recovery)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val validateEma: Button = findViewById(R.id.buttonValidateEmail)
        validateEma.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, ResetPassword::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }

        val backlogin: TextView = findViewById(R.id.textViewBackToLogin)

        backlogin.setOnClickListener {
            // 1. Crear la intención para ir a SegundaActivity
            val intent = Intent(this, LoginActivity::class.java)

            // 2. Iniciar la nueva Activity
            startActivity(intent)
        }
    }
}