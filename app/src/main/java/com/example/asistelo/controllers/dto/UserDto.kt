package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.Date

data class UserDto(
    //Al crear los campos que pueden ser null, poner ?, y poner bien los Json Property. Implementar el serializable para que se puedan pasar los datos entre intents
    @JsonProperty("id") val id: Int,
    @JsonProperty("dateCre") @JsonFormat(pattern = "dd/MM/yyyy") val dateCre: Date?,
    @JsonProperty("dateMod") @JsonFormat(pattern = "dd/MM/yyyy") val dateMod: Date?,
    @JsonProperty("email") val email: String,
    @JsonProperty("firstSurname") val firstSurname: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("pass") val pass: String?,
    @JsonProperty("secondSurname") val secondSurname: String?,
    @JsonProperty("userCre") val userCre: Int?,
    @JsonProperty("userMod") val userMod: Int?,
    @JsonProperty("role") val role: RolDto?,
    @JsonProperty("absenceList") val absenceList: List<AbsenceDto>?,
    @JsonProperty("subjectList") val subjectList: List<SubjectDto>?,
    @JsonProperty("classList") val classList: List<ClassDto>?
) : Serializable
