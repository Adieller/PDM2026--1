package com.example.pruebaproyectov1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.pruebaproyectov1.R
import com.example.pruebaproyectov1.viewmodel.LoginViewModel

// Definición de colores para el tema neón
val NeonPurple = Color(0xFFB388FF)
val NeonCyan = Color(0xFF00E5FF)
val NeonBlue = Color(0xFF2979FF)
val DarkBackground = Color(0xFF121212).copy(alpha = 0.8f)
val FieldBackgroundColor = Color(0xFF1E1E1E).copy(alpha = 0.7f)
@Composable
fun LoginScreen(navController: NavHostController, viewModel: LoginViewModel = viewModel()) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        // Hacemos el fondo del Scaffold transparente para que se vea tu imagen
        containerColor = Color.Transparent
    ) { innerPadding ->

        // Box principal que contiene el fondo y el contenido
        Box(
            modifier = Modifier
                .fillMaxSize()
            // (Opcional) Si quieres que el fondo respete las barras de sistema, mueve este padding al Column.
            // Si quieres fondo pantalla completa, déjalo sin padding aquí y úsalo solo en el contenido.
        ) {
            // 1. Fondo de imagen (Ocupa toda la pantalla)
            Image(
                painter = painterResource(id = R.drawable.fondofallenangels),
                contentDescription = "Fondo Cyberpunk",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Capa de oscurecimiento
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground) // Asegúrate de tener definido este color
            )

            // 3. Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding) // AQUI aplicamos el padding del Scaffold
                    .padding(horizontal = 32.dp), // Tu padding lateral original
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
                            color = NeonCyan, // Asegúrate de tener definido este color
                            offset = Offset(0f, 0f),
                            blurRadius = 20f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(48.dp))

                // Campo de texto de Usuario
                NeonTextField(
                    value = viewModel.usuario,
                    onValueChange = { viewModel.usuario = it},
                    placeholder = "NOMBRE DE USUARIO",
                    error = viewModel.usuarioError
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Campo de texto de Contraseña
                NeonTextField(
                    value = viewModel.contrasena,
                    onValueChange = {viewModel.contrasena = it},
                    placeholder = "CONTRASEÑA",
                    isPassword = true,
                    error = viewModel.contrasenaError
                )

                Spacer(modifier = Modifier.height(36.dp))

                // Botón de Entrar
                Button(
                    //En el onne click poner la funcion que valide el nombre y contraseña para que pase a ala siguiente ventana
                    onClick = {
                        viewModel.validarLogin(
                            onLoginSuccess = { userId,nombreUsuario ->
                                navController.navigate("home/$userId/$nombreUsuario") {
                                    popUpTo("login") { inclusive = true }
                                }
                            }
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .shadow(
                            elevation = 12.dp,
                            shape = RoundedCornerShape(28.dp),
                            spotColor = NeonPurple, // Asegúrate de tener definido este color
                            ambientColor = NeonPurple
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp),
                    shape = RoundedCornerShape(28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(NeonPurple, NeonBlue) // Asegúrate de tener definidos estos colores
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
                TextButton(onClick = { navController.navigate("recovery") }) {
                    Text(
                        text = "SE ME OLVIDÓ LA CONTRASEÑA",
                        color = NeonCyan,
                        textDecoration = TextDecoration.Underline
                    )
                }

                TextButton(onClick = { navController.navigate("registro") }) {
                    Text(
                        text = "REGISTRATE",
                        color = NeonCyan,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NeonTextField(
    value: String,
    onValueChange: (String) -> Unit,
    error: String? = null,
    keyboardType: KeyboardType = KeyboardType.Text, // 1. Recibimos el tipo aquí
    placeholder: String,
    isPassword: Boolean = false
) {
    // 2. Necesitamos un contenedor (Column) para apilar el Input y el Texto de Error
    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(text = placeholder, color = Color.Gray) },
            modifier = Modifier.fillMaxWidth(),
            isError = error != null,
            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
            shape = RoundedCornerShape(16.dp),

            // 3. Colores corregidos para Material 3
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = FieldBackgroundColor,
                unfocusedContainerColor = FieldBackgroundColor,
                focusedBorderColor = NeonCyan,
                unfocusedBorderColor = NeonPurple.copy(alpha = 0.6f),
                cursorColor = NeonCyan,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                errorContainerColor = FieldBackgroundColor, // Mantiene fondo si hay error
                errorBorderColor = MaterialTheme.colorScheme.error
            ),

            // 4. Lógica de VisualTransformation (Contraseña vs Texto)
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,

            // 5. Unificación de KeyboardOptions (Evita duplicados)
            keyboardOptions = KeyboardOptions(
                keyboardType = if (isPassword) KeyboardType.Password else keyboardType
            ),
            singleLine = true
        )

        // 6. El bloque de error va FUERA del OutlinedTextField, pero DENTRO de la Column
        if (error != null) {
            Text(
                text = error,
                color = MaterialTheme.colorScheme.error,
                // Nota: displayMedium es gigante, he puesto bodySmall para un error normal
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
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