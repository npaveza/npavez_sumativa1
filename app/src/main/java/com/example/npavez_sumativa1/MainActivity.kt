package com.example.npavez_sumativa1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.npavez_sumativa1.ui.screens.LoginScreen
import com.example.npavez_sumativa1.ui.screens.RegistroScreen
import com.example.npavez_sumativa1.ui.screens.RecuperarPasswordScreen
import com.example.npavez_sumativa1.ui.theme.NpavezSumativa1Theme

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

fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("registro") { RegistroScreen(navController) }
        composable("recuperar") { RecuperarPasswordScreen(navController) }
    }
}