package com.tuempresa.osornomarket.backend.repository

import com.tuempresa.osornomarket.backend.data.database.ProgramsTable
import com.tuempresa.osornomarket.backend.data.mapper.toDomainProgram
import com.tuempresa.osornomarket.backend.domain.model.Program
import com.tuempresa.osornomarket.backend.domain.repository.ProgramRepositoryContract
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProgramRepository : ProgramRepositoryContract {
    override suspend fun getAll(): List<Program> = transaction {
        ProgramsTable.selectAll().map { it.toDomainProgram() }
    }

    override suspend fun getById(id: Int): Program? = transaction {
        ProgramsTable.select { ProgramsTable.id eq id }
            .map { it.toDomainProgram() }
            .singleOrNull()
    }

    override suspend fun create(program: Program): Int = transaction {
        ProgramsTable.insert {
            it[name] = program.name
            it[description] = program.description
            it[price] = program.price
        } get ProgramsTable.id
    }

    override suspend fun update(id: Int, program: Program): Boolean = transaction {
        ProgramsTable.update({ ProgramsTable.id eq id }) {
            it[name] = program.name
            it[description] = program.description
            it[price] = program.price
        } > 0
    }

    override suspend fun delete(id: Int): Boolean = transaction {
        ProgramsTable.deleteWhere { ProgramsTable.id eq id } > 0
    }
}
