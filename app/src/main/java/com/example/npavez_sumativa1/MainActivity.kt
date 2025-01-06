package com.example.npavez_sumativa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.npavez_sumativa1.ui.screens.LoginScreen
import com.example.npavez_sumativa1.ui.screens.RegistroScreen
import com.example.npavez_sumativa1.ui.screens.RecuperarPasswordScreen
import com.example.npavez_sumativa1.ui.screens.HomeScreen
import com.example.npavez_sumativa1.ui.theme.NpavezSumativa1Theme
import com.example.npavez_sumativa1.data.currentUser

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
    NavHost(navController = navController, startDestination = if (currentUser != null) "home" else "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("recuperar") { RecuperarPasswordScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}