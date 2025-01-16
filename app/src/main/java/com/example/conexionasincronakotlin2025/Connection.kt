package com.example.conexionasincronakotlin2025

import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

object Connection {

    @Throws(IOException::class)
    fun connectJava(url: URL?): Result {
        val result = Result()
        result.code = 500

        val connection: HttpURLConnection = url!!.openConnection() as HttpURLConnection
        result.code = connection.responseCode
        if (result.code == HttpURLConnection.HTTP_OK)
            result.content = connection.inputStream.bufferedReader().readText()
        else
            result.message = "Error: ${result.code.toString()} \n Error en la conexión a $url"
        connection.disconnect()
        return result
    }
}