package com.example.npavez_sumativa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.npavez_sumativa1.ui.screens.LoginScreen
import com.example.npavez_sumativa1.ui.screens.RegistroScreen
import com.example.npavez_sumativa1.ui.screens.RecuperarPasswordScreen
import com.example.npavez_sumativa1.ui.screens.HomeScreen
import com.example.npavez_sumativa1.ui.screens.RecipeScreen
import com.example.npavez_sumativa1.ui.screens.RecipeDetailScreen
import com.example.npavez_sumativa1.ui.theme.NpavezSumativa1Theme
import com.example.npavez_sumativa1.data.currentUser
import com.example.npavez_sumativa1.data.recetas

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NpavezSumativa1Theme {
                val navController = rememberNavController()
                AppNavigation(navController)
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
        composable("detalle_receta/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toIntOrNull() ?: 0
            val recetaSeleccionada = recetas.getOrNull(index)
            if (recetaSeleccionada != null) {
                RecipeDetailScreen(navController, recetaSeleccionada)
            } else {
                Text("Receta no encontrada")
            }
        }
    }
}