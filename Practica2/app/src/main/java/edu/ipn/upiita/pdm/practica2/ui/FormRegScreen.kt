package edu.ipn.upiita.pdm.practica2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import edu.ipn.upiita.pdm.practica2.viewmodel.FormRegViewModel

import edu.ipn.upiita.pdm.practica2.R


@Composable
fun FormRegScreen(navController: NavHostController,viewModel: FormRegViewModel = viewModel(),onBack: () -> Unit){


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {

            Image(
                painter = painterResource(id = R.drawable.fondo_homefa),
                contentDescription = "Fondo Cyberpunk",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Capa de oscurecimiento
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground)
            )

            // 3. Contenido principal del registro
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Título
                Text(
                    text = "REGISTRO DE USUARIO",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 28.sp, // Un poco más pequeño que el login para que quepa todo
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(
                            color = NeonCyan,
                            offset = Offset(0f, 0f),
                            blurRadius = 20f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(40.dp))

                // --- CAMPOS DE TEXTO (Usando tu composable NeonTextField) ---

                // 1. Usuario
                NeonTextField(
                    value = viewModel.usuario,
                    onValueChange = { viewModel.usuario = it },
                    placeholder = "USUARIO",
                    error = viewModel.usuarioError

                )

                Spacer(modifier = Modifier.height(20.dp))

                // 2. Contraseña
                NeonTextField(
                    value = viewModel.contrasena,
                    onValueChange = { viewModel.contrasena = it },
                    placeholder = "CONTRASEÑA",
                    error = viewModel.contrasenaError,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Confirmar Contraseña
                NeonTextField(
                    value = viewModel.confirmarContrasena,
                    onValueChange = { viewModel.confirmarContrasena = it },
                    placeholder = "CONFIRMAR CONTRASEÑA",
                    error = viewModel.confirmarContrasenaError,
                    isPassword = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 4. Email
                NeonTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    error = viewModel.emailError,
                    placeholder = "EMAIL",
                    keyboardType = KeyboardType.Email

                )

                Spacer(modifier = Modifier.height(20.dp))

                // 5. Pregunta de seguridad
                NeonTextField(
                    value = viewModel.pregunta,
                    onValueChange = { viewModel.pregunta = it },
                    error = viewModel.preguntaError,
                    placeholder = "PREGUNTA DE SEGURIDAD",

                )
                Spacer(modifier = Modifier.height(20.dp))

                // 6. Respuesta de seguridad
                NeonTextField(
                    value = viewModel.respuesta,
                    onValueChange = { viewModel.respuesta = it },
                    error = viewModel.respuestaError,
                    placeholder = "Respuesta",

                )

                Spacer(modifier = Modifier.height(20.dp))

                // --- BOTÓN DE REGISTRARSE ---
                Button(
                    onClick = {
                        viewModel.intentarRegistro {
                            onBack()
                        }
                    },
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
                                    colors = listOf(NeonPurple, NeonBlue)
                                ),
                                shape = RoundedCornerShape(28.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "REGISTRARSE",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Enlace para volver al Login
                TextButton(onClick = { navController.navigate("login") }) {
                    Text(
                        text = "¿Ya tienes una cuenta? Inicia sesión",
                        color = NeonCyan,
                        // Puedes quitar el subrayado si prefieres que se parezca más a la imagen
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}
