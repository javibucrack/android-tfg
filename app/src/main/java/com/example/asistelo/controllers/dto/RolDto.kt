package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class RolDto(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String
)
