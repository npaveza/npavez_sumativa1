package com.example.npavez_sumativa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.usuarios // Se usa la lista global de usuarios

@Composable
fun RecuperarPasswordScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var simulatedEmailContent by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (email.isBlank()) {
                message = "Por favor, ingresa un correo electrónico."
                simulatedEmailContent = null
            } else {
                val user = usuarios.find { it.first == email }
                if (user != null) {
                    message = "Correo de recuperación generado exitosamente."
                    simulatedEmailContent = """
                        Asunto: Recuperación de Contraseña
                        Para: $email
                        
                        Estimado usuario,
                        
                        Hemos recibido tu solicitud para recuperar tu contraseña. 
                        Tu contraseña actual es: ${user.second}
                        
                        Por favor, asegúrate de mantener esta información segura.
                        
                        Saludos cordiales,
                        El equipo de la aplicación.
                    """.trimIndent()
                } else {
                    message = "El correo ingresado no está registrado."
                    simulatedEmailContent = null
                }
            }
        }) {
            Text("Generar Correo de Recuperación")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (message.isNotEmpty()) {
            Text(
                text = message,
                color = androidx.compose.ui.graphics.Color.Blue,
                style = androidx.compose.material3.MaterialTheme.typography.bodySmall
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        simulatedEmailContent?.let {
            Text(
                text = it,
                color = androidx.compose.ui.graphics.Color.DarkGray,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { navController.navigate("login") }) {
            Text("Volver al Inicio de Sesión")
        }
    }
}