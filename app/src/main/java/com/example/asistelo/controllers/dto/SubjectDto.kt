package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class SubjectDto(

    @JsonProperty("id") val id: Int,
    @JsonProperty("totalHours") val total_hours: Int?,
    @JsonProperty("dateCre") @JsonFormat(pattern = "dd/MM/yyyy") val date_cre: Date?,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val date_mod: Date?,
    @JsonProperty("name") val name: String,
    @JsonProperty("userCre") val usu_cre: UserDto?,
    @JsonProperty("userMod") val usu_mod: UserDto?,
    @JsonProperty("student") val students: List<UserDto>?,
    @JsonProperty("teacher") val teacher: List<UserDto>?,
    @JsonProperty("class") val classList: List<ClassDto>?,
    @JsonProperty("absence") val absence: List<AbsenceDto>?,
    @JsonProperty("percentage")val percentage:Double?
) : Serializable
