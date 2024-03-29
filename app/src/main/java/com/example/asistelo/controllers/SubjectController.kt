package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.SubjectDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Interfaz que contiene los métodos del controlador de asignaturas.
 */
interface SubjectController {

    @GET("/subjects")
    fun getAllSubjects(
    ): Call<List<SubjectDto>>

    @POST("/subject/{idUserCre}")
    fun addSubject(
        @Body subjectDto: SubjectDto,
        @Path("idUserCre") idUserCre: Int
    ): Call<Void>
}