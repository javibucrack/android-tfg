package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.Date

data class UserDto(
    //Al crear los campos que pueden ser null, poner ?, y poner bien los Json Property
    @JsonProperty("id") val id: Int,
    @JsonProperty("dateCre") @JsonFormat(pattern = "yyyy-MM-dd") val date_cre: Date,
    @JsonProperty("dateMod") @JsonFormat(pattern = "yyyy-MM-dd") val date_mod: Date?,
    @JsonProperty("email") val email: String,
    @JsonProperty("firstSurname") val first_surname: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("pass") val pass: String,
    @JsonProperty("secondSurname") val second_surname: String?,
    @JsonProperty("userCre") val usu_cre: Int,
    @JsonProperty("userMod") val usu_mod: Int?,
    @JsonProperty("role") val role: RolDto,
) : Serializable
