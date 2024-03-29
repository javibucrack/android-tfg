package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.ClassDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interfaz que contiene los métodos del controlador de clases.
 */
interface ClassController {

    @GET("/classesList")
    fun getAllClasses(
    ): Call<List<ClassDto>>

    @GET("/classes/{idTeacher}")
    fun getClasses(
        @Path("idTeacher") idTeacher: Int,
    ): Call<List<ClassDto>>

    @POST("/class/{idUserCre}")
    fun addClass(
        @Body classDto: ClassDto,
        @Path("idUserCre") idUserCre: Int
    ): Call<Void>
}