package com.example.actividad52miniproyecto01

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    navController: NavController,
    matricula: String,
    nombre: String,
    carrera: String,
    turno: String,
    estatus: Boolean
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Estudiante") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Resumen de Registro", fontSize = 22.sp, fontWeight = FontWeight.Bold)
            HorizontalDivider()

            Text("Matrícula:", fontWeight = FontWeight.Bold)
            Text(matricula)

            Text("Nombre:", fontWeight = FontWeight.Bold)
            Text(nombre)

            Text("Carrera:", fontWeight = FontWeight.Bold)
            Text(carrera)

            Text("Turno:", fontWeight = FontWeight.Bold)
            Text(turno)

            Text("Estatus:", fontWeight = FontWeight.Bold)
            Text(
                text = if (estatus) "Activo" else "Inactivo",
                color = if (estatus) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.weight(1f))

            OutlinedButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Formulario")
            }
        }
    }
}