package com.example.asistelo.config

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

/**
 * Clase que contiene la configuración del Retrofit.
 * En caso de querer cambiar a donde te vas a conectar, ya sea localhost
 * u otra IP, debes cambiar el valor de "BASE_URL".
 * Si quieres usar localhost, tienes que usar la IP: 10.0.2.2
 */
object RetrofitClient {
    private const val BASE_URL = "http://192.168.1.130:8080"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
    }
}