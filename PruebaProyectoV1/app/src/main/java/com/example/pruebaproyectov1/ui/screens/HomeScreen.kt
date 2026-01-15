package com.example.pruebaproyectov1.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.pruebaproyectov1.R
import com.example.pruebaproyectov1.model.Task
import com.example.pruebaproyectov1.viewmodel.TaskViewModel

// Definición de colores (Asegúrate de tenerlos, si no, ajústalos a tu Theme)
val DarkOverlay = Color(0xFF000000).copy(alpha = 0.85f) // Un poco más oscuro para resaltar la lista
val CardBackground = Color(0xFF1E1E1E).copy(alpha = 0.7f)

@Composable
fun HomeScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel, // Inyectamos el ViewModel
    userId: Long, // Necesitamos el ID para cargar las tareas
    userName: String
) {
    // 1. Estado para el menú y la nueva tarea
    var expanded by remember { mutableStateOf(false) }
    var newTaskTitle by remember { mutableStateOf("") }

    // 2. Observamos la lista de tareas del ViewModel
    val tasks by taskViewModel.tasks.collectAsState()

    // 3. Cargar tareas al iniciar la pantalla
    LaunchedEffect(userId) {
        taskViewModel.setUserId(userId)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // --- FONDO ---
        Image(
            painter = painterResource(id = R.drawable.fondo_homefa), // Tu fondo
            contentDescription = "Fondo Home",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
        Box(modifier = Modifier.fillMaxSize().background(DarkOverlay))

        // --- CONTENIDO ---
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // --- BARRA SUPERIOR (Igual que antes) ---
            TopBarCyberpunk(userName, expanded, { expanded = it }, navController)

            Spacer(modifier = Modifier.height(20.dp))

            // --- SECCIÓN DE USUARIO (Versión Compacta) ---
            // He compactado un poco el avatar para dar espacio a la lista
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(80.dp) // Más pequeño para que quepan las tareas
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .border(2.dp, Brush.sweepGradient(listOf(NeonCyan, NeonPurple, NeonCyan)), CircleShape)
                    )
                    Image(
                        painter = painterResource(id = R.drawable.icono_fallenangels),
                        contentDescription = "Avatar",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.size(70.dp).clip(CircleShape)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "AGENTE: ${userName.uppercase()}",
                        style = TextStyle(color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "ESTADO: EN LÍNEA",
                        style = TextStyle(color = NeonCyan, fontSize = 12.sp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // --- INPUT NUEVA TAREA ---
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = newTaskTitle,
                    onValueChange = { newTaskTitle = it },
                    label = { Text("NUEVA MISIÓN", color = NeonCyan.copy(alpha = 0.7f)) },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = NeonPurple,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = NeonCyan,
                        focusedLabelColor = NeonCyan,
                        unfocusedLabelColor = NeonPurple
                    ),
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                // Botón de agregar (+)
                IconButton(
                    onClick = {
                        if (newTaskTitle.isNotBlank()) {
                            taskViewModel.addTask(newTaskTitle)
                            newTaskTitle = "" // Limpiar campo
                        }
                    },
                    modifier = Modifier
                        .size(50.dp)
                        .background(
                            brush = Brush.linearGradient(listOf(NeonPurple, NeonCyan)),
                            shape = RoundedCornerShape(12.dp)
                        )
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar", tint = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // --- LISTA DE TAREAS ---
            Text(
                "LISTA DE MISIONES",
                color = NeonCyan,
                modifier = Modifier.align(Alignment.Start),
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tasks, key = { it.id }) { task ->
                    TaskItemCyberpunk(
                        task = task,
                        onToggle = { taskViewModel.toggleTaskCompletion(task) },
                        onDelete = { taskViewModel.deleteTask(task) }
                    )
                }
            }
        }
    }
}

// --- COMPONENTE: ITEM DE TAREA ---
@Composable
fun TaskItemCyberpunk(
    task: Task,
    onToggle: () -> Unit,
    onDelete: () -> Unit
) {
    val borderColor = if (task.isDone) Color.Gray else NeonCyan
    val textColor = if (task.isDone) Color.Gray else Color.White
    val textDecoration = if (task.isDone) TextDecoration.LineThrough else TextDecoration.None

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    brush = if (task.isDone) SolidColor(Color.Gray) else Brush.horizontalGradient(listOf(NeonCyan, NeonPurple)),
                ),
                shape = RoundedCornerShape(12.dp)
            )
            .background(CardBackground, RoundedCornerShape(12.dp))
            .clickable { onToggle() } // Tocar toda la tarjeta cambia el estado
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Checkbox estilo icono
            Icon(
                imageVector = if (task.isDone) Icons.Default.TaskAlt else Icons.Default.RadioButtonUnchecked,
                contentDescription = "Estado",
                tint = if (task.isDone) NeonPurple else NeonCyan,
                modifier = Modifier.size(28.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Título de la tarea
            Text(
                text = task.title,
                style = TextStyle(
                    color = textColor,
                    fontSize = 16.sp,
                    textDecoration = textDecoration
                ),
                modifier = Modifier.weight(1f)
            )

            // Botón Eliminar
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Eliminar",
                    tint = Color(0xFFFF4444) // Rojo para borrar
                )
            }
        }
    }
}

// --- COMPONENTE: BARRA SUPERIOR (Extraído para limpieza) ---
@Composable
fun TopBarCyberpunk(
    userName: String,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    navController: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .height(50.dp)
                .border(
                    border = BorderStroke(2.dp, Brush.horizontalGradient(listOf(NeonPurple, NeonCyan))),
                    shape = RoundedCornerShape(12.dp)
                )
                .background(Color.Black.copy(alpha = 0.5f), RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "FALLEN ANGELS",
                style = TextStyle(color = Color.White, fontSize = 20.sp, fontWeight = FontWeight.Bold, letterSpacing = 1.sp)
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Box {
            IconButton(onClick = { onExpandedChange(true) }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menú", tint = NeonCyan, modifier = Modifier.size(32.dp))
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { onExpandedChange(false) },
                offset = DpOffset(x = 0.dp, y = 10.dp),
                modifier = Modifier.background(Color(0xFF1A1A1A)).border(1.dp, NeonPurple, RoundedCornerShape(4.dp))
            ) {
                DropdownMenuItem(
                    text = { Text("CERRAR SESIÓN", color = NeonCyan) },
                    onClick = {
                        navController.navigate("login") {
                            popUpTo(0) { inclusive = true }
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}