package edu.ipn.upiita.pdm.proyecto1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import edu.ipn.upiita.pdm.proyecto1.ui.TasksScreen
import edu.ipn.upiita.pdm.proyecto1.ui.theme.DarkGrayBackground
import edu.ipn.upiita.pdm.proyecto1.ui.theme.Proyecto1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Proyecto1Theme(darkTheme = true) { // Forzamos el modo oscuro
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkGrayBackground // Usamos el color de fondo definido
                ) {
                    TasksScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun mainScreenPreview(){
    TasksScreen()
}

