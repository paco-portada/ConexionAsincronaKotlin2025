package com.example.conexionasincronakotlin2025

import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.HttpURLConnection.HTTP_MOVED_PERM
import java.net.URL

object Connection {

    @Throws(IOException::class)
    fun connectJava(url: URL?): Result {
        val result = Result()
        result.code = 500

        var connection: HttpURLConnection = url!!.openConnection() as HttpURLConnection
        result.code = connection.responseCode

        if (result.code == HttpURLConnection.HTTP_OK)
            result.content = connection.inputStream.bufferedReader().readText()
        else
            if (result.code == HTTP_MOVED_PERM) {
                // hacer una nueva petición a la redirección recibida
                // obtener cabeceras de un servidor web:
                // https://javierin.com/visualizar-cabeceras-http-de-un-servidor-remoto/
                // curl -I http://dam.org.es/ficheros/frases.html
                val newUrl = connection.getHeaderField("Location")
                Log.e("redirección", newUrl.toString())
                connection = URL(newUrl).openConnection() as HttpURLConnection
                result.code = connection.responseCode
                if (result.code == HttpURLConnection.HTTP_OK)
                    result.content = connection.inputStream.bufferedReader().readText()
                else
                    result.message =
                        "Error: ${result.code.toString()} \n Error en la conexión a $url \n Mensaje: ${connection.responseMessage}"
            } else
                result.message =
                    "Error: ${result.code.toString()} \n Error en la conexión a $url \n Mensaje: ${connection.responseMessage}"

        connection.disconnect()

        return result
    }
}