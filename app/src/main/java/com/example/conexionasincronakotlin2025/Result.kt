package com.example.conexionasincronakotlin2025

class Result {
    var code = 0 //indica el código de estado devuelto por el servidor web
    lateinit var message: String //información del error
    lateinit var content: String //fichero descargado
}