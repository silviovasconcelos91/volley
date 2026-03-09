package vasconcelos.silvio.volley.controller.dto

import vasconcelos.silvio.volley.domain.model.Level

data class PlayerDto(
    val id: Long? = null,
    val name: String,
    val surname: String,
    val email: String,
    val age: Int,
    val level: Level
)