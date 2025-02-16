package com.example.npavez_sumativa1.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.npavez_sumativa1.data.Receta
import com.google.firebase.database.*

@Composable
fun RecipeDetailScreen(navController: NavController, recetaId: String) {
    var receta by remember { mutableStateOf<Receta?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    // Cargar la receta desde Firebase
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
        if (isLoading) {
            CircularProgressIndicator()
        } else if (receta == null) {
            Text("Receta no encontrada.")
        } else {
            Text(
                text = receta!!.nombre,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "Ingredientes:",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            receta!!.ingredientes.forEach { ingrediente ->
                Text("- $ingrediente", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Pasos:",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(receta!!.pasos, style = MaterialTheme.typography.bodyMedium)
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Volver")
        }
    }
}
