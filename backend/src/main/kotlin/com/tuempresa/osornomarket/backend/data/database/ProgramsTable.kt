package com.tuempresa.osornomarket.backend.data.database

import org.jetbrains.exposed.sql.Table

object ProgramsTable : Table("programs") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val description = text("description")
    val price = long("price")

    override val primaryKey = PrimaryKey(id)
}
