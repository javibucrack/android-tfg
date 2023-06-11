package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

/**
 * Clase que contiene los atributos de las clases.
 */
data class ClassDto(
    @JsonProperty("id") val id: Int?,
    @JsonProperty("dateCre") @JsonFormat(pattern = "dd/MM/yyyy") val dateCre: Date?,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val dateMod: Date?,
    @JsonProperty("name") val name: String?,
    @JsonProperty("userCre") val userCre: UserDto?,
    @JsonProperty("userMod") val userMod: UserDto?,
    @JsonProperty("students") val students: List<UserDto>?,
    @JsonProperty("teachers") val teachers: List<UserDto>?
) : Serializable
