package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class SubjectDto(

    @JsonProperty("id") val id: Int,
    @JsonProperty("totalHours") val totalHours: Int?,
    @JsonProperty("dateCre") @JsonFormat(pattern = "dd/MM/yyyy") val dateCre: Date?,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val dateMod: Date?,
    @JsonProperty("name") val name: String,
    @JsonProperty("userCre") val userCre: UserDto?,
    @JsonProperty("userMod") val userMod: UserDto?,
    @JsonProperty("student") val student: List<UserDto>?,
    @JsonProperty("teacher") val teacher: List<UserDto>?,
    @JsonProperty("classes") val classes: List<ClassDto>?,
    @JsonProperty("absence") val absence: List<AbsenceDto>?,
    @JsonProperty("percentage")val percentage:Double?
) : Serializable
