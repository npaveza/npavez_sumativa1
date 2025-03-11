package com.example.npavez_sumativa1.ui.screens

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.Receta
import com.google.firebase.database.*
import java.util.Locale

@Composable
fun RecipeScreen(navController: NavController, esVegana: Boolean) {
    val context = LocalContext.current
    val tts = remember { TextToSpeech(context) { } }
    var recetas by remember { mutableStateOf<List<Receta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }

    // Cargar recetas desde Firebase
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("recetas")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaRecetas = snapshot.children.mapNotNull { it.getValue(Receta::class.java) }

                // Filtrar recetas según esVegana
                recetas = if (esVegana) {
                    listaRecetas.filter { it.esVegana }
                } else {
                    listaRecetas.filter { !it.esVegana }
                }
                isLoading = false
            }
            override fun onCancelled(error: DatabaseError) {
                isLoading = false
            }
        })
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Recetas Disponibles",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if (isLoading) {
            CircularProgressIndicator()
        } else if (recetas.isEmpty()) {
            Text("No hay recetas disponibles.")
        } else {
            LazyColumn {
                items(recetas.size) { index ->
                    ClickableText(
                        text = AnnotatedString(recetas[index].nombre),
                        onClick = { navController.navigate("detalle_receta/${recetas[index].id}") },
                        modifier = Modifier.padding(8.dp)
                    )
                    Divider()
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para escuchar nombres de recetas
        Button(onClick = {
            val nombres = recetas.joinToString(", ") { it.nombre }
            tts.speak("Las recetas disponibles son: $nombres. Diga el nombre de una para seleccionarla.", TextToSpeech.QUEUE_FLUSH, null, null)
        }) {
            Text("Escuchar recetas")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para activar reconocimiento de voz
        Button(onClick = {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-MX")
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Diga el nombre de una receta")
            }
            speechRecognizer.startListening(intent)
        }) {
            Text("Decir receta")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { navController.navigate("home") }) {
            Text("Volver a Home")
        }
    }

    DisposableEffect(Unit) {
        speechRecognizer.setRecognitionListener(object : android.speech.RecognitionListener {
            override fun onResults(results: Bundle?) {
                val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION) ?: return
                val recognizedText = matches.firstOrNull()?.lowercase(Locale.ROOT) ?: return

                val recetaEncontrada = recetas.find { it.nombre.lowercase(Locale.ROOT) == recognizedText }
                if (recetaEncontrada != null) {
                    navController.navigate("detalle_receta/${recetaEncontrada.id}")
                } else {
                    tts.speak("No se encontró la receta mencionada.", TextToSpeech.QUEUE_FLUSH, null, null)
                }
            }

            override fun onError(error: Int) {
                tts.speak("Error al reconocer la voz. Intente de nuevo.", TextToSpeech.QUEUE_FLUSH, null, null)
            }

            override fun onReadyForSpeech(params: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(rmsdB: Float) {}
            override fun onBufferReceived(buffer: ByteArray?) {}
            override fun onEndOfSpeech() {}
            override fun onPartialResults(partialResults: Bundle?) {}
            override fun onEvent(eventType: Int, params: Bundle?) {}
        })

        onDispose {
            speechRecognizer.destroy()
            tts.shutdown()
        }
    }
}
