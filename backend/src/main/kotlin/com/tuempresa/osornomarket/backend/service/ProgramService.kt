package com.tuempresa.osornomarket.backend.service

import com.tuempresa.osornomarket.backend.data.dto.CreateProgramRequest
import com.tuempresa.osornomarket.backend.data.dto.ProgramDto
import com.tuempresa.osornomarket.backend.data.mapper.toDto
import com.tuempresa.osornomarket.backend.domain.model.Program
import com.tuempresa.osornomarket.backend.domain.repository.ProgramRepositoryContract

class ProgramService(private val repository: ProgramRepositoryContract) {
    
    suspend fun getAllPrograms(): List<ProgramDto> {
        return repository.getAll().map { it.toDto() }
    }

    suspend fun getProgramById(id: Int): ProgramDto {
        val program = repository.getById(id) ?: throw NotFoundException("Programa no encontrado")
        return program.toDto()
    }

    suspend fun createProgram(req: CreateProgramRequest): Int {
        val program = Program(
            id = 0,
            name = req.name,
            description = req.description,
            price = req.price
        )
        return repository.create(program)
    }

    suspend fun updateProgram(id: Int, req: CreateProgramRequest) {
        val program = Program(
            id = id,
            name = req.name,
            description = req.description,
            price = req.price
        )
        if (!repository.update(id, program)) {
            throw NotFoundException("No se pudo actualizar: Programa no encontrado")
        }
    }

    suspend fun deleteProgram(id: Int) {
        if (!repository.delete(id)) {
            throw NotFoundException("No se pudo eliminar: Programa no encontrado")
        }
    }
}
