package com.tuempresa.osornomarket.backend.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProgramDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Long
)

@Serializable
data class CreateProgramRequest(
    val name: String,
    val description: String,
    val price: Long
)
