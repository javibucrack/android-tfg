package com.example.asistelo.controllers

import com.example.asistelo.controllers.dto.ClassDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ClassController {

    @GET("/classes/{idTeacher}")
    fun getClasses(
        @Path("idTeacher") idTeacher: Int,
    ): Call<List<ClassDto>>
}