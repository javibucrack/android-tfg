package com.example.asistelo.controllers.dto

import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

/**
 * Clase que contiene los atributos de los roles.
 */
data class RolDto(
    @JsonProperty("id") var id: Int?,
    @JsonProperty("name") var name: String?
) : Serializable
