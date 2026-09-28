package com.example.actividad52miniproyecto01

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.actividad52miniproyecto01.data.PreferencesManager
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(navController: NavController) {
    val context = LocalContext.current
    val preferencesManager = remember { PreferencesManager(context) }


    var matricula by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var carrera by remember { mutableStateOf("Ingeniería de Software") }
    var turno by remember { mutableStateOf("Matutino") }
    var estatus by remember { mutableStateOf(true) }


    var expanded by remember { mutableStateOf(false) }
    val opcionesCarrera = listOf("Ingeniería de Software", "Ciberseguridad", "Redes", "Sistemas Computacionales")


    LaunchedEffect(Unit) {
        matricula = preferencesManager.getMatricula()
        nombre = preferencesManager.getNombre()
        carrera = preferencesManager.getCarrera()
        turno = preferencesManager.getTurno()
        estatus = preferencesManager.getEstatus()
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Sistema de Registro",
                fontSize = 24.sp,
                style = MaterialTheme.typography.headlineMedium
            )

            HorizontalDivider()


            OutlinedTextField(
                value = matricula,
                onValueChange = { matricula = it },
                label = { Text("Matrícula") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre Completo") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )


            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = it }
            ) {
                OutlinedTextField(
                    value = carrera,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Carrera") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    opcionesCarrera.forEach { seleccion ->
                        DropdownMenuItem(
                            text = { Text(seleccion) },
                            onClick = {
                                carrera = seleccion
                                expanded = false
                            }
                        )
                    }
                }
            }


            Text("Turno:")
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = (turno == "Matutino"),
                    onClick = { turno = "Matutino" }
                )
                Text("Matutino", modifier = Modifier.padding(end = 16.dp))

                RadioButton(
                    selected = (turno == "Vespertino"),
                    onClick = { turno = "Vespertino" }
                )
                Text("Vespertino")
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Estatus: ${if (estatus) "Activo" else "Inactivo"}")
                Switch(
                    checked = estatus,
                    onCheckedChange = { estatus = it }
                )
            }

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = {

                    preferencesManager.saveStudent(matricula, nombre, carrera, turno, estatus)
                    Toast.makeText(context, "Datos guardados localmente", Toast.LENGTH_SHORT).show()


                    val nombreEncoded = URLEncoder.encode(nombre.ifEmpty { "Sin Nombre" }, StandardCharsets.UTF_8.toString())
                    val carreraEncoded = URLEncoder.encode(carrera, StandardCharsets.UTF_8.toString())
                    val matriculaSafe = matricula.ifEmpty { "SinMatricula" }


                    navController.navigate("detail/$matriculaSafe/$nombreEncoded/$carreraEncoded/$turno/$estatus")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Guardar y Ver Detalle")
            }
        }
    }
}