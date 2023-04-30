package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class ClassDto(
    @JsonProperty("id") val id: Int,
    @JsonProperty("dateCre") @JsonFormat(pattern = "yyyy-MM-dd") val date_cre: Date,
    @JsonProperty("dateMod") @JsonFormat(pattern = "yyyy-MM-dd") val date_mod: Date?,
    @JsonProperty("name") val name: String,
    @JsonProperty("userCre") val usu_cre: Int,
    @JsonProperty("userMod") val usu_mod: Int?,
) : Serializable
