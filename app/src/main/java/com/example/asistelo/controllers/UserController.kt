package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.http.*

/**
 * Interfaz que contiene los métodos del controlador de usuarios.
 */
interface UserController {

    @GET("/user/{email}/{pass}")
    fun login(
        @Path("email") email: String,
        @Path("pass") pass: String
    ): Call<UserDto>

    @GET("/user/{idUser}")
    fun getUser(
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

    @GET("/users/{role}/{classId}/{subjectId}")
    fun getAllStudents(
        @Path("role") role: String,
        @Path("classId") classId: Int,
        @Path("subjectId") subjectId: Int
    ): Call<List<UserDto>>

    @PUT("/user/newPass")
    fun changePass(
        @Body user: UserDto
    ): Call<Void>
}