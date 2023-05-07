package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.AbsenceDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface AbsenceController {

    @POST("/absence/{idSubject}/{idStudent}/{idTeacher}")
    fun newAbsence(
        @Body absence: AbsenceDto,
        @Path("idSubject") idSubject: Int,
        @Path("idStudent") idStudent: Int,
        @Path("idTeacher") idTeacher: Int,
    ): Call<Void>
}