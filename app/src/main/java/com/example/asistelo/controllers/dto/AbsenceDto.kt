package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

/**
 * Clase que contiene los atributos de las ausencias.
 */
data class AbsenceDto(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("numHours") val numHours: Int?,
    @JsonProperty("date") @JsonFormat(pattern = "dd/MM/yyyy") val date: Date?,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val dateMod: Date?,
    @JsonProperty("teacher") val teacher: UserDto?,
    @JsonProperty("userMod") val userMod: UserDto?,
    @JsonProperty("student") val student: UserDto?,
    @JsonProperty("subject") val subject: SubjectDto?
) : Serializable
