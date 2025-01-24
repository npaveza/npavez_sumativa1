package com.example.npavez_sumativa1.data

val usuarios = mutableListOf<Pair<String, String>>()

var currentUser: Pair<String, String>? = null

fun logout() {
    currentUser = null
}
