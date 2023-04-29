package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserController {

    @GET("/user/{email}/{pass}")
    fun searchUser(
        @Path("email") email: String,
        @Path("pass") pass: String
    ): Call<UserDto>
}