package com.example.npavez_sumativa1.data

val usuarios = mutableListOf<Pair<String, String>>() // Lista global de usuarios

var currentUser: Pair<String, String>? = null

fun logout() {
    currentUser = null
}