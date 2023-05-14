package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.AbsenceDto
import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.http.*

interface UserController {

    @GET("/user/{email}/{pass}")
    fun getUser(
        @Path("email") email: String,
        @Path("pass") pass: String
    ): Call<UserDto>

    @GET("/user/{idUser}")
    fun getStudent(
        @Path("idUser") idUser: Int
    ): Call<UserDto>

    @GET("user/{idUser}/absences")
    fun getAbsences(
        @Path("idUser") idUser: Int
    ): Call<UserDto>

    @POST("/user/{idUserCre}")
    fun addUser(
        @Body user: UserDto,
        @Path("idUserCre") idUserCre: Int
    ): Call<Void>
}