package edu.ipn.upiita.pdm.practica2.ui
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.ipn.upiita.pdm.practica2.R


val DarkOverlay = Color(0xFF000000).copy(alpha = 0.7f)
val CardBackground = Color(0xFF1E1E1E).copy(alpha = 0.5f)

@Composable
fun HomeScreen(navController: NavHostController, userName: String) {
    // Estado para controlar el menú desplegable
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        // 1. Imagen de Fondo
        Image(
            // REEMPLAZA CON TU RECURSO DE FONDO
            painter = painterResource(id = R.drawable.fondo_homefa),
            contentDescription = "Fondo Home",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // 2. Capa oscura para mejorar legibilidad
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkOverlay)
        )

        // 3. Contenido Principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- BARRA SUPERIOR (Toolbar Personalizada) ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Título enmarcado
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp)
                        .border(
                            border = BorderStroke(
                                width = 2.dp,
                                brush = Brush.horizontalGradient(listOf(NeonPurple, NeonCyan))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "FALLEN ANGELS",
                        style = TextStyle(
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Botón de Menú (3 puntos)
                Box {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menú",
                            tint = NeonCyan,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    // Menú Desplegable
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 0.dp, y = 10.dp),
                        modifier = Modifier
                            .background(Color(0xFF1A1A1A))
                            .border(1.dp, NeonPurple, RoundedCornerShape(4.dp))
                    ) {
                        DropdownMenuItem(
                            text = { Text("CAMBIAR CONTRASEÑA", color = NeonCyan) },
                            onClick = { navController.navigate("recovery") }
                        )
                        DropdownMenuItem(
                            text = { Text("CERRAR SESIÓN", color = NeonCyan) },
                            onClick = {
                                navController.navigate("login") {
                                    //Bloque de codigo para eliminar la pila deanavegacion una vez navegado a la screen deseada
                                    popUpTo(0){
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // --- BIENVENIDA ---
            Text(
                text = "BIENVENIDO  ${userName.uppercase()}",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    shadow = androidx.compose.ui.graphics.Shadow(
                        color = NeonCyan, blurRadius = 10f
                    )
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // --- ICONO DE USUARIO ---
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(140.dp)
            ) {
                // Borde brillante exterior
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .border(3.dp, Brush.sweepGradient(listOf(NeonCyan, NeonPurple, NeonCyan)), CircleShape)
                )

                // Imagen del Avatar
                Image(
                    // REEMPLAZA CON TU RECURSO DE ICONO/AVATAR
                    painter = painterResource(id = R.drawable.icono_fallenangels),
                    contentDescription = "Avatar Usuario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(50.dp))

            // --- TARJETAS DE INFORMACIÓN (Misiones / Estado) ---

            CyberpunkInfoCard(
                text = "MISIÓN ACTUAL: PROTEGER LA CIUDAD"
            )

            Spacer(modifier = Modifier.height(20.dp))

            CyberpunkInfoCard(
                text = "ESTADO: CONECTADO"
            )
        }
    }
}

// Componente reutilizable para las tarjetas de información estilo Cyberpunk
@Composable
fun CyberpunkInfoCard(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(listOf(NeonCyan, NeonPurple))
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .background(CardBackground, RoundedCornerShape(12.dp))
            .shadow(8.dp, RoundedCornerShape(12.dp), ambientColor = NeonCyan, spotColor = NeonCyan),
        contentAlignment = Alignment.Center
    ) {
        // Líneas decorativas internas (opcional, para dar más detalle como en la imagen)
        Column(Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(10.dp))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NeonCyan.copy(alpha = 0.3f)))
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(NeonCyan.copy(alpha = 0.3f)))
            Spacer(modifier = Modifier.height(10.dp))
        }

        // Texto central
        Text(
            text = text,
            style = TextStyle(
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}
