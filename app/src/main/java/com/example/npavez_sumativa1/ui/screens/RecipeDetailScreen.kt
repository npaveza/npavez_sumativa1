package com.example.npavez_sumativa1.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.Receta
import java.util.Locale

@Composable
fun RecipeDetailScreen(navController: NavController, receta: Receta) {
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) {} }

    LaunchedEffect(Unit) {
        tts.language = Locale("es", "MX")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra de color como encabezado
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF6200EE)) // Morado
        )

        // Título de la receta con barra decorativa
        Text(
            text = receta.nombre,
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(16.dp)
                .background(
                    color = Color(0xFFBB86FC), // Color lila
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de ingredientes
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color(0xFFEDE7F6), // Fondo suave
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Ingredientes:",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF6200EE))
                )
                Spacer(modifier = Modifier.height(8.dp))
                receta.ingredientes.forEach { ingrediente ->
                    Text(
                        text = "- $ingrediente",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Sección de pasos
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .background(
                    color = Color(0xFFE3F2FD), // Fondo azul claro
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Pasos:",
                    style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF0288D1))
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = receta.pasos,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para reproducir la receta
        Button(
            onClick = {
                val texto = "Ingredientes: ${receta.ingredientes.joinToString(", ")}.\nPasos: ${receta.pasos}"
                tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde
        ) {
            Text("Reproducir receta", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para volver
        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)) // Rojo
        ) {
            Text("Volver", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Barra decorativa en la parte inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF6200EE)) // Morado
        )
    }
}
