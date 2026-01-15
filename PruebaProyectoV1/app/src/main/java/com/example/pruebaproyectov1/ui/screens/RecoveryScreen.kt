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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

import com.example.pruebaproyectov1.R

import com.example.pruebaproyectov1.data.database.AppDataBase
import com.example.pruebaproyectov1.data.repository.UserRepository
import com.example.pruebaproyectov1.viewmodel.RecoveryViewModel
import com.example.pruebaproyectov1.viewmodel.RecoveryViewModelFactory

@Composable
fun RecoveryScreen(
    navController: NavHostController,
    viewModel: RecoveryViewModel = viewModel(factory = RecoveryViewModelFactory(
        UserRepository(
            AppDataBase.getDatabase(LocalContext.current).userDao()
        )
    )
    ) // Asegúrate de usar tu Factory correcto aquí
) {
    // --- UI DE FONDO Y ESTRUCTURA (Idéntico a FormRegScreen) ---
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent
    ) { innerPadding ->

        Box(modifier = Modifier.fillMaxSize()) {
            // 1. Fondo de imagen
            Image(
                painter = painterResource(id = R.drawable.fondo_homefa), // Asegúrate de tener este recurso
                contentDescription = "Fondo Cyberpunk",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // 2. Capa de oscurecimiento
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(DarkBackground) // Tu color DarkBackground
            )

            // 3. Contenido principal
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // --- TÍTULO ---
                Text(
                    text = "RECUPERAR CUENTA",
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        shadow = Shadow(
                            color = NeonCyan, // Tu color NeonCyan
                            offset = Offset(0f, 0f),
                            blurRadius = 20f
                        )
                    )
                )

                Spacer(modifier = Modifier.height(30.dp))

                // --- MENSAJES DE ERROR ---
                viewModel.errorMessage?.let { error ->
                    Text(
                        text = error,
                        color = Color.Red, // O usa un color NeonRed si tienes
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                }

                // --- LÓGICA DE PASOS (Steps) ---
                when (viewModel.currentStep) {
                    0 -> StepEmail(viewModel)
                    1 -> StepSecurityQuestion(viewModel)
                    2 -> StepNewPassword(viewModel, navController)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- BOTÓN VOLVER (Común a todos los pasos) ---
                TextButton(onClick = { navController.navigate("login_screen") }) {
                    Text(
                        text = "Volver al inicio de sesión",
                        color = NeonCyan,
                        textDecoration = TextDecoration.Underline
                    )
                }
            }
        }
    }
}

// --- SUB-COMPONENTES PARA CADA PASO (Para mantener el código limpio) ---

@Composable
fun StepEmail(viewModel: RecoveryViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        NeonTextField(
            value = viewModel.email,
            onValueChange = { viewModel.email = it },
            placeholder = "INGRESA TU EMAIL",
            keyboardType = KeyboardType.Email,
            error = null // O añade lógica de error visual si quieres
        )

        Spacer(modifier = Modifier.height(20.dp))

        CyberpunkButton(text = "BUSCAR USUARIO") {
            viewModel.buscarUsuario()
        }
    }
}

@Composable
fun StepSecurityQuestion(viewModel: RecoveryViewModel) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Mostramos la pregunta con estilo
        Text(
            text = "¿${viewModel.preguntaSeguridad}?",
            style = TextStyle(
                color = Color.White,
                fontSize = 18.sp,
                fontStyle = FontStyle.Italic
            ),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(20.dp))

        NeonTextField(
            value = viewModel.respuestaUsuario,
            onValueChange = { viewModel.respuestaUsuario = it },
            placeholder = "TU RESPUESTA",
            error = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        CyberpunkButton(text = "VERIFICAR") {
            viewModel.validarRespuesta()
        }
    }
}

@Composable
fun StepNewPassword(viewModel: RecoveryViewModel, navController: NavHostController) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        NeonTextField(
            value = viewModel.nuevaContrasena,
            onValueChange = { viewModel.nuevaContrasena = it },
            placeholder = "NUEVA CONTRASEÑA",
            isPassword = true,
            error = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        NeonTextField(
            value = viewModel.confirmarNuevaContrasena,
            onValueChange = { viewModel.confirmarNuevaContrasena = it },
            placeholder = "CONFIRMAR CONTRASEÑA",
            isPassword = true,
            error = null
        )

        Spacer(modifier = Modifier.height(20.dp))

        CyberpunkButton(text = "ACTUALIZAR") {
            viewModel.actualizarContrasena {
                navController.navigate("login") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

// --- COMPONENTE DE BOTÓN REUTILIZABLE (Extraído de tu FormRegScreen) ---
@Composable
fun CyberpunkButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = NeonPurple, // Tu color NeonPurple
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
                        colors = listOf(NeonPurple, NeonBlue) // Tus colores Neon
                    ),
                    shape = RoundedCornerShape(28.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}