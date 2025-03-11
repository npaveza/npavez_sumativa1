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
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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
                    // 10 RECETAS GENERALES
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Arroz con Mariscos",
                        ingredientes = listOf(
                            "Arroz",
                            "Mariscos",
                            "Cebolla",
                            "Ajo",
                            "Tomate",
                            "Pimentón",
                            "Sal"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega los mariscos y cocina hasta que estén tiernos.\n3. Mezcla con arroz cocido, tomate picado, pimentón y sal.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Pollo al Curry",
                        ingredientes = listOf(
                            "Pollo",
                            "Cebolla",
                            "Ajo",
                            "Curry",
                            "Leche de coco",
                            "Arroz"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega el pollo y cocina hasta que esté dorado.\n3. Mezcla con curry, leche de coco y arroz cocido.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tallarines con Salsa de Tomate",
                        ingredientes = listOf(
                            "Tallarines",
                            "Tomate",
                            "Cebolla",
                            "Ajo",
                            "Albahaca",
                            "Queso"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega los tomates y cocina hasta que estén suaves.\n3. Mezcla con albahaca, queso y tallarines cocidos.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Churrasco con Chimichurri",
                        ingredientes = listOf(
                            "Churrasco",
                            "Perejil",
                            "Ajo",
                            "Limón",
                            "Aceite de oliva"
                        ),
                        pasos = "1. Mezcla perejil, ajo, limón y aceite de oliva para hacer el chimichurri.\n2. Asa el churrasco a la parrilla hasta que esté cocido.\n3. Sirve con chimichurri.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Salmón a la Parrilla",
                        ingredientes = listOf(
                            "Salmón",
                            "Limón",
                            "Aceite de oliva",
                            "Sal",
                            "Pimienta"
                        ),
                        pasos = "1. Sazona el salmón con limón, aceite de oliva, sal y pimienta.\n2. Asa a la parrilla hasta que esté cocido.\n3. Sirve caliente.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Pollo Frito",
                        ingredientes = listOf("Pollo", "Harina", "Huevo", "Pan rallado", "Aceite"),
                        pasos = "1. Mezcla harina, huevo y pan rallado para hacer la mezcla de rebozo.\n2. Reboza el pollo con la mezcla.\n3. Fríe en aceite caliente hasta que esté dorado y crujiente.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Ensalada de Atún",
                        ingredientes = listOf("Atún", "Lechuga", "Tomate", "Cebolla", "Mayonesa"),
                        pasos = "1. Mezcla atún, lechuga, tomate y cebolla.\n2. Aliña con mayonesa.\n3. Sirve fresco.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tacos de Carne",
                        ingredientes = listOf(
                            "Carne molida",
                            "Cebolla",
                            "Ajo",
                            "Tortillas",
                            "Salsa",
                            "Queso"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega la carne molida y cocina hasta que esté dorada.\n3. Sirve en tortillas con salsa y queso.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Sopa de Verduras",
                        ingredientes = listOf(
                            "Verduras",
                            "Caldo",
                            "Cebolla",
                            "Ajo",
                            "Aceite de oliva"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega las verduras y caldo.\n3. Cocina hasta que las verduras estén tiernas. Sirve caliente.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Empanadas de Carne",
                        ingredientes = listOf(
                            "Carne molida",
                            "Cebolla",
                            "Ajo",
                            "Masa de empanadas",
                            "Huevo"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega la carne molida y cocina hasta que esté dorada.\n3. Rellena las masas de empanadas con la mezcla y hornea hasta que estén doradas.",
                        esVegana = false
                    ),
                    // 10 RECETAS VEGETARIANAS
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Risotto de Champiñones",
                        ingredientes = listOf(
                            "Arroz arborio",
                            "Champiñones",
                            "Caldo de verduras",
                            "Queso parmesano",
                            "Cebolla"
                        ),
                        pasos = "1. Sofríe la cebolla y los champiñones en aceite de oliva hasta que estén suaves.\n2. Agrega el arroz y caldo poco a poco.\n3. Incorpora queso parmesano y sirve.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Gnocchi de Espinaca con Salsa de Queso",
                        ingredientes = listOf("Harina", "Espinaca", "Huevo", "Queso crema"),
                        pasos = "1. Mezcla harina y espinaca hasta formar una masa.\n2. Corta y hierve los gnocchi.\n3. Sirve con salsa de queso crema.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tarta de Zapallito Italiano",
                        ingredientes = listOf(
                            "Masa de tarta",
                            "Zapallito italiano",
                            "Huevo",
                            "Queso"
                        ),
                        pasos = "1. Cocina el zapallito y mézclalo con huevo.\n2. Coloca sobre la masa y agrega queso.\n3. Hornea hasta que cuaje.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Sopa Crema de Zapallo",
                        ingredientes = listOf("Zapallo", "Cebolla", "Crema", "Ajo"),
                        pasos = "1. Cocina el zapallo y la cebolla.\n2. Procesa hasta obtener una crema.\n3. Agrega crema y sirve caliente.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Ensalada de Quinoa",
                        ingredientes = listOf("Quinoa", "Palta", "Tomate", "Pepino", "Limón"),
                        pasos = "1. Cocina la quinoa y deja enfriar.\n2. Mezcla con palta, tomate y pepino.\n3. Aliña con limón y sal.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tortilla de Espinacas",
                        ingredientes = listOf("Espinacas", "Huevo", "Cebolla", "Ajo", "Sal"),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega las espinacas y cocina hasta que estén tiernas.\n3. Mezcla con huevo y cocina hasta que esté cuajado.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Sopa de Lentejas",
                        ingredientes = listOf(
                            "Lentejas",
                            "Cebolla",
                            "Ajo",
                            "Zanahoria",
                            "Caldo de verduras"
                        ),
                        pasos = "1. Sofríe la cebolla y el ajo en aceite de oliva hasta que estén suaves.\n2. Agrega las lentejas, zanahoria y caldo de verduras.\n3. Cocina hasta que las lentejas estén tiernas.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Ensalada de Garbanzos",
                        ingredientes = listOf("Garbanzos", "Cebolla", "Pepino", "Tomate", "Limón"),
                        pasos = "1. Mezcla los garbanzos con cebolla, pepino y tomate.\n2. Aliña con limón y sal.\n3. Sirve fresco.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Tarta de Verduras",
                        ingredientes = listOf("Masa de tarta", "Verduras", "Huevo", "Queso"),
                        pasos = "1. Cocina las verduras y mézclalas con huevo.\n2. Coloca sobre la masa y agrega queso.\n3. Hornea hasta que cuaje.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Salsa de Aguacate",
                        ingredientes = listOf("Aguacate", "Cebolla", "Ajo", "Limón", "Sal"),
                        pasos = "1. Mezcla el aguacate con cebolla, ajo y limón.\n2. Salpimienta al gusto.\n3. Sirve fresco.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Ensalada de Frutas",
                        ingredientes = listOf("Frutas", "Limón", "Miel"),
                        pasos = "1. Mezcla las frutas y aliña con limón y miel.\n2. Sirve fresco.",
                        esVegana = true
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Batido de Plátano",
                        ingredientes = listOf("Plátano", "Leche", "Miel"),
                        pasos = "1. Mezcla el plátano con leche y miel.\n2. Licúa hasta obtener un batido suave.\n3. Sirve fresco.",
                        esVegana = false
                    ),
                    Receta(
                        id = ref.push().key ?: "",
                        nombre = "Galletas de Avena",
                        ingredientes = listOf("Avena", "Harina", "Azúcar", "Huevo", "Mantequilla"),
                        pasos = "1. Mezcla la avena con harina, azúcar y huevo.\n2. Agrega mantequilla y mezcla hasta obtener una masa.\n3. Hornea hasta que estén doradas.",
                        esVegana = true
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

    @Composable
    fun AppNavigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "login") {
            composable("login") { LoginScreen(navController) }
            composable("registro") { RegistroScreen(navController) }
            composable("recuperar") { RecuperarPasswordScreen(navController) }
            composable("home") { HomeScreen(navController) }
            composable("menu_recetas") { MenuRecetasScreen(navController) }
            composable("recetas_generales") { RecipeScreen(navController, false) }
            composable("recetas_veganas") { RecipeScreen(navController, true) }

            // Ajuste para pasar la ID de la receta
            composable("detalle_receta/{id}") { backStackEntry ->
                val recetaId = backStackEntry.arguments?.getString("id") ?: ""
                RecipeDetailScreen(navController, recetaId)
            }
        }
    }
}
