package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class AbsenceDto(
    @JsonProperty("id") val id: Int,
    @JsonProperty("numHours") val num_hours: Int,
    @JsonProperty("date") @JsonFormat(pattern = "dd/MM/yyyy") val date: Date,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val date_mod: Date?,
    @JsonProperty("teacher") val usu_cre: UserDto,
    @JsonProperty("userMod") val usu_mod: UserDto?,
    @JsonProperty("student") val student: UserDto,
) : Serializable
