package com.example.npavez_sumativa1.ui.screens

import android.speech.tts.TextToSpeech
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.Receta
import com.google.firebase.database.FirebaseDatabase
import java.util.Locale

@Composable
fun RecipeDetailScreen(navController: NavController, recetaId: String) {
    var receta by remember { mutableStateOf<Receta?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) {} }

    // Configurar idioma de TTS
    LaunchedEffect(Unit) {
        tts.language = Locale("es", "MX")
    }

    // Cargar receta desde Firebase
    LaunchedEffect(recetaId) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("recetas").child(recetaId)

        ref.get().addOnSuccessListener { snapshot ->
            receta = snapshot.getValue(Receta::class.java)
            isLoading = false
        }.addOnFailureListener {
            println("Error al cargar la receta: ${it.message}")
            isLoading = false
        }
    }

    // Interfaz de usuario
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Barra superior de color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFF6200EE)) // Morado
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (receta == null) {
            Text("Receta no encontrada.")
        } else {
            // Título con estilo
            Text(
                text = receta!!.nombre,
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 24.sp),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color(0xFFBB86FC), shape = RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de ingredientes con fondo colorido
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFEDE7F6), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Ingredientes:",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF6200EE))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    receta!!.ingredientes.forEach { ingrediente ->
                        Text(text = "- $ingrediente", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            // Botón para leer los ingredientes
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    val texto = "Ingredientes: ${receta!!.ingredientes.joinToString(", ")}"
                    tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)) // Verde
            ) {
                Text("Leer Ingredientes", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Sección de pasos con fondo colorido
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFFE3F2FD), shape = RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = "Pasos:",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color(0xFF0288D1))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = receta!!.pasos, style = MaterialTheme.typography.bodyMedium)
                }
            }

            // Botón para leer los pasos
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    tts.speak("Pasos: ${receta!!.pasos}", TextToSpeech.QUEUE_FLUSH, null, null)
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)) // Azul
            ) {
                Text("Leer Pasos", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de volver
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)) // Rojo
            ) {
                Text("Volver", color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Barra inferior decorativa
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .background(Color(0xFF6200EE)) // Morado
            )
        }
    }
}
