package com.example.npavez_sumativa1.data

data class Receta(
    val nombre: String,
    val ingredientes: List<String>,
    val pasos: String
)

val recetas = listOf(
    Receta(
        nombre = "Spaghetti a la Boloñesa",
        ingredientes = listOf("Spaghetti", "Carne molida", "Tomate", "Cebolla", "Ajo"),
        pasos = "1. Cocina el spaghetti.\n2. Prepara la salsa boloñesa.\n3. Mezcla y sirve."
    ),
    Receta(
        nombre = "Ensalada César",
        ingredientes = listOf("Lechuga", "Crutones", "Queso parmesano", "Aderezo César"),
        pasos = "1. Lava la lechuga.\n2. Mezcla los ingredientes.\n3. Sirve con aderezo."
    ),
    Receta(
        nombre = "Tacos de Pollo",
        ingredientes = listOf("Tortillas", "Pollo desmenuzado", "Lechuga", "Tomate", "Queso rallado"),
        pasos = "1. Calienta las tortillas.\n2. Cocina el pollo con tus especias favoritas.\n3. Coloca el pollo en las tortillas y agrega lechuga, tomate y queso."
    ),
    Receta(
        nombre = "Sopa de Verduras",
        ingredientes = listOf("Zanahoria", "Papa", "Cebolla", "Apio", "Caldo de pollo o vegetales"),
        pasos = "1. Corta las verduras en trozos pequeños.\n2. Cocina las verduras en el caldo a fuego medio hasta que estén tiernas.\n3. Sirve caliente."
    ),
    Receta(
        nombre = "Pizza Margherita",
        ingredientes = listOf("Masa de pizza", "Salsa de tomate", "Mozzarella", "Hojas de albahaca", "Aceite de oliva"),
        pasos = "1. Precalienta el horno a 200°C.\n2. Extiende la salsa de tomate sobre la masa de pizza.\n3. Añade mozzarella y hornea hasta que el queso se derrita.\n4. Decora con hojas de albahaca y un chorrito de aceite de oliva antes de servir."
    )
)