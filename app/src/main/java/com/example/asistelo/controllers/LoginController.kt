package com.example.asistelo.controllers

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginController {

    @GET("/login/{idLogin}/{email}/{pass}")
    fun searchUser(
        @Path("idLogin") idLogin: Int,
        @Path("email") email: String,
        @Path("pass") pass: String
    ): Call<Boolean>
}