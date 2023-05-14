package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

data class RolDto(
    @JsonProperty("id") val id: Int,
    @JsonProperty("name") val name: String?
) : Serializable
