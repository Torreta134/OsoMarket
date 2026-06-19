package com.tuempresa.osornomarket.backend.domain.repository

import com.tuempresa.osornomarket.backend.domain.model.Program

interface ProgramRepositoryContract {
    suspend fun getAll(): List<Program>
    suspend fun getById(id: Int): Program?
    suspend fun create(program: Program): Int
    suspend fun update(id: Int, program: Program): Boolean
    suspend fun delete(id: Int): Boolean
}
