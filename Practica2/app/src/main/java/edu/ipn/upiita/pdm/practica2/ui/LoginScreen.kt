package edu.ipn.upiita.pdm.practica2.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import edu.ipn.upiita.pdm.practica2.R

// Definición de colores para el tema neón
val NeonPurple = Color(0xFFB388FF)
val NeonCyan = Color(0xFF00E5FF)
val NeonBlue = Color(0xFF2979FF)
val DarkBackground = Color(0xFF121212).copy(alpha = 0.8f)
val FieldBackgroundColor = Color(0xFF1E1E1E).copy(alpha = 0.7f)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoginScreen(navController: NavHostController){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // 1. Fondo de imagen
        Image(
            // Reemplaza 'R.drawable.cyberpunk_bg' con el ID de tu imagen de fondo
            painter = painterResource(id = R.drawable.fondofallenangels),
            contentDescription = "Fondo Cyberpunk",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa de oscurecimiento (opcional, para mejorar el contraste)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
        )

        // 3. Contenido principal en una columna centrada
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Título con efecto neón
            Text(
                text = "FALLEN ANGELS\nLOGIN",
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

            Spacer(modifier = Modifier.height(48.dp))

            // Campo de texto de Usuario
            NeonTextField(
                value = "",
                onValueChange = {},
                placeholder = "NOMBRE DE USUARIO"
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo de texto de Contraseña
            NeonTextField(
                value = "",
                onValueChange = {},
                placeholder = "CONTRASEÑA",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(36.dp))

            // Botón de Entrar con gradiente y brillo
            Button(
                onClick = { /* Acción de login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 12.dp,
                        shape = RoundedCornerShape(28.dp),
                        spotColor = NeonPurple,
                        ambientColor = NeonPurple
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Para usar el gradiente del Box interno
                ),
                contentPadding = PaddingValues(0.dp),
                shape = RoundedCornerShape(28.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(NeonPurple, NeonBlue)
                            ),
                            shape = RoundedCornerShape(28.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "ENTRAR",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Enlaces de texto
            TextButton(onClick = { /* Acción de recuperar contraseña */ }) {
                Text(
                    text = "SE ME OLVIDÓ LA CONTRASEÑA",
                    color = NeonCyan,
                    textDecoration = TextDecoration.Underline
                )
            }

            TextButton(onClick = { /* Acción de registrar */ }) {
                Text(
                    text = "REGISTRAR NUEVO USUARIO",
                    color = NeonCyan,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = FieldBackgroundColor, // En M3 usa focusedContainerColor/unfocusedContainerColor en lugar de solo containerColor
            unfocusedContainerColor = FieldBackgroundColor,
            focusedBorderColor = NeonCyan,
            unfocusedBorderColor = NeonPurple.copy(alpha = 0.6f),
            cursorColor = NeonCyan,
            // Asegúrate de que los colores del texto también sean correctos si es necesario
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else androidx.compose.ui.text.input.VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions.Default,
        singleLine = true
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 740)
@Composable
fun LoginScreenPreview() {
    MaterialTheme {
        // Creamos un navController ficticio solo para la preview
        val navController = rememberNavController()

        // Pasamos el navController ficticio a la pantalla
        LoginScreen(navController = navController)
    }
}