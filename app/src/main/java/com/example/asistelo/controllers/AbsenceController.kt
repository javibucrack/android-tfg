package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.AbsenceDto
import retrofit2.Call
import retrofit2.http.*

/**
 * Interfaz que contiene los métodos del controlador de ausencias.
 */
interface AbsenceController {

    @GET("/absences/{teacher}/{subjectId}")
    fun getAbsences(
        @Path("teacher") teacher: Int,
        @Path("subjectId") subjectId: Int,
    ): Call<List<AbsenceDto>>

    @POST("/absence/{idSubject}/{idStudent}/{idTeacher}")
    fun newAbsence(
        @Body absence: AbsenceDto,
        @Path("idSubject") idSubject: Int,
        @Path("idStudent") idStudent: Int,
        @Path("idTeacher") idTeacher: Int,
    ): Call<Void>

    @DELETE("/absence/{absenceId}")
    fun deleteAbsence(
        @Path("absenceId") absenceId: Int
    ): Call<Void>
}