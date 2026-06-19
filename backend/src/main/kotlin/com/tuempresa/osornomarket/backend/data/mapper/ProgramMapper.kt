package com.tuempresa.osornomarket.backend.data.mapper

import com.tuempresa.osornomarket.backend.data.database.ProgramsTable
import com.tuempresa.osornomarket.backend.data.dto.ProgramDto
import com.tuempresa.osornomarket.backend.domain.model.Program
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toDomainProgram() = Program(
    id = this[ProgramsTable.id],
    name = this[ProgramsTable.name],
    description = this[ProgramsTable.description],
    price = this[ProgramsTable.price]
)

fun Program.toDto() = ProgramDto(
    id = id,
    name = name,
    description = description,
    price = price
)
