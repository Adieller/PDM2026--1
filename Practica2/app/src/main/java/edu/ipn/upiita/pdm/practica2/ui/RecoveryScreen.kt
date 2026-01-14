package edu.ipn.upiita.pdm.practica2.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@Composable
fun RecoveryScreen(navController: NavHostController){
    Text(
        text = "FALLEN ANGELS\n\nBienvenido al recovery",
        style = TextStyle(
            color = Color.White,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            shadow = Shadow(
                color = NeonCyan,
                offset = Offset(0f, 0f),
                blurRadius = 20f
            )
        )
    )
}