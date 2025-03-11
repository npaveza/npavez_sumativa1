package com.example.npavez_sumativa1.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.currentUser
import com.example.npavez_sumativa1.data.logout

@Composable
fun HomeScreen(navController: NavController) {
    val email = currentUser?.first ?: "Invitado"

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF1976D2), Color(0xFF42A5F5))))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Bienvenido $email", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            // Botón para recetas generales (con carne)
            Button(
                onClick = { navController.navigate("recetas_generales") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF8B0A1A), // Color rojo-marrón para dar un toque cálido
                    contentColor = Color.White
                )
            ) {
                Text("Ver Recetas Generales")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para recetas vegetarianas
            Button(
                onClick = { navController.navigate("recetas_veganas") },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3E8E41), // Color verde para dar un toque natural
                    contentColor = Color.White
                )
            ) {
                Text("Ver Recetas Vegetarianas")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    logout()
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Cerrar Sesión", color = Color.White)
            }
        }
    }
}