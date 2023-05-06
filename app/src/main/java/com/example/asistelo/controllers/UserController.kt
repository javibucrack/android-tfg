package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.UserDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserController {

    @GET("/user/{email}/{pass}")
    fun getUser(
        @Path("email") email: String,
        @Path("pass") pass: String
    ): Call<UserDto>

//    @GET("/user/{role}")
//    fun getAllStudents(
//        @Query("subject") subject: String,
//        @Query("class") className: String,
//        @Path("role") role: String,
//    ): Call<List<UserDto>>

    @GET("/user/{idUser}")
    fun getStudent(
        @Path("idUser") idUser: Int
    ): Call<UserDto>

    @GET("user/{idUser}/absences")
    fun getAbsences(
        @Path("idUser") idUser: Int
    ): Call<UserDto>
}