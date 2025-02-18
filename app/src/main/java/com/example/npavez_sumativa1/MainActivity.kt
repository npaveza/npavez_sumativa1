package com.example.npavez_sumativa1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.npavez_sumativa1.ui.screens.*
import com.example.npavez_sumativa1.ui.theme.NpavezSumativa1Theme
import com.example.npavez_sumativa1.data.Receta
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificar y solicitar permisos de micrófono
        solicitarPermisos()
        // Inicializa las recetas en la base de datos
        guardarRecetasIniciales()

        setContent {
            NpavezSumativa1Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }

    private fun solicitarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 1)
        }
    }

    // Inserta las recetas iniciales si no existen
    private fun guardarRecetasIniciales() {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("recetas")

        ref.get().addOnSuccessListener { snapshot ->
            if (!snapshot.exists()) {
                val recetas = listOf(
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Spaghetti a la Boloñesa",
                        ingredientes = listOf("Spaghetti", "Carne molida", "Tomate", "Cebolla", "Ajo"),
                        pasos = "1. Cocina el spaghetti.\n2. Prepara la salsa boloñesa sofriendo cebolla con ajo, echando sal y pimienta a gusto, carne molida y tomate. \n3. Mezcla y sirve."
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Ensalada César",
                        ingredientes = listOf("Lechuga", "Crutones", "Queso parmesano", "Aderezo César"),
                        pasos = "1. Lava la lechuga.\n2. Mezcla los ingredientes.\n3. Sirve con aderezo."
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tacos de Pollo",
                        ingredientes = listOf("Tortillas", "Pollo desmenuzado", "Lechuga", "Tomate", "Queso rallado"),
                        pasos = "1. Calienta las tortillas.\n2. Cocina el pollo con tus especias favoritas.\n3. Coloca el pollo en las tortillas y agrega lechuga, tomate y queso."
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Sopa de Verduras",
                        ingredientes = listOf("Zanahoria", "Papa", "Cebolla", "Apio", "Caldo de pollo"),
                        pasos = "1. Corta las verduras en trozos pequeños.\n2. Cocina las verduras en el caldo a fuego medio hasta que estén tiernas.\n3. Sirve caliente."
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Pizza Margherita",
                        ingredientes = listOf("Masa de pizza", "Salsa de tomate", "Mozzarella", "Hojas de albahaca", "Aceite de oliva"),
                        pasos = "1. Precalienta el horno a 200°C.\n2. Extiende la salsa de tomate sobre la masa.\n3. Añade mozzarella y hornea hasta que el queso se derrita.\n4. Decora con hojas de albahaca y un chorrito de aceite de oliva."
                    )
                )

                recetas.forEach { receta ->
                    ref.child(receta.id).setValue(receta)
                        .addOnSuccessListener { println("Receta guardada: ${receta.nombre}") }
                        .addOnFailureListener { println("Error al guardar la receta: ${it.message}") }
                }
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("recuperar") { RecuperarPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
        composable("recetas") { RecipeScreen(navController) }

        // Ajuste para pasar la ID de la receta
        composable("detalle_receta/{id}") { backStackEntry ->
            val recetaId = backStackEntry.arguments?.getString("id") ?: ""
            RecipeDetailScreen(navController, recetaId)
        }
    }
}
