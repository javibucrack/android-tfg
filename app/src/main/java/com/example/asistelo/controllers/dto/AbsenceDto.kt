package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable
import java.util.*

data class AbsenceDto(
    @JsonProperty("id") val id: Int,
    @JsonProperty("numHours") val num_hours: Int,
    @JsonProperty("date") @JsonFormat(pattern = "yyyy-MM-dd") val date: Date,
    @JsonProperty("dateCre") @JsonFormat(pattern = "yyyy-MM-dd") val date_cre: Date,
    @JsonProperty("dateMod") @JsonFormat(pattern = "yyyy-MM-dd") val date_mod: Date?,
    @JsonProperty("userCre") val usu_cre: Int,
    @JsonProperty("userMod") val usu_mod: Int?,
) : Serializable
