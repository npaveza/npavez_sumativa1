package com.example.npavez_sumativa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.Receta
import com.google.firebase.database.*

@Composable
fun RecipeScreen(navController: NavController) {
    var recetas by remember { mutableStateOf<List<Receta>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar recetas desde Firebase
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("recetas")

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaRecetas = mutableListOf<Receta>()
                for (data in snapshot.children) {
                    val receta = data.getValue(Receta::class.java)
                    if (receta != null) {
                        // Conversión de MutableList a List
                        receta.ingredientes = receta.ingredientes.toList()
                        println("Receta cargada: ${receta.nombre}")
                        listaRecetas.add(receta)
                    }
                }
                recetas = listaRecetas
                println("Total recetas cargadas: ${recetas.size}")
                isLoading = false
            }

            override fun onCancelled(error: DatabaseError) {
                println("Error al cargar recetas: ${error.message}")
                isLoading = false
            }
        })
    }

    // Interfaz de usuario
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
            CircularProgressIndicator()  // Muestra un indicador de carga
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
        Button(onClick = { navController.navigate("home") }) {
            Text("Volver a Home")
        }
    }
}
