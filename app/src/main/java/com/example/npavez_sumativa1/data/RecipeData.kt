package com.example.npavez_sumativa1.data

data class Receta(
    var id: String = "",
    var nombre: String = "",
    var ingredientes: List<String> = emptyList(),
    var pasos: String = "",
    var esVegana: Boolean = false // Nuevo campo para diferenciar recetas veganas
)