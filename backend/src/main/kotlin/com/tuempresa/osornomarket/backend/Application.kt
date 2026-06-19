package com.tuempresa.osornomarket.backend

import com.tuempresa.osornomarket.backend.data.database.ProgramsTable
import com.tuempresa.osornomarket.backend.data.database.UsersTable
import com.tuempresa.osornomarket.backend.repository.ProgramRepository
import com.tuempresa.osornomarket.backend.repository.UserRepository
import com.tuempresa.osornomarket.backend.routes.authRoutes
import com.tuempresa.osornomarket.backend.routes.programRoutes
import com.tuempresa.osornomarket.backend.service.AuthService
import com.tuempresa.osornomarket.backend.service.ProgramService
import com.tuempresa.osornomarket.backend.service.TokenService
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    
    // Configuración de Content Negotiation (JSON)
    install(ContentNegotiation) {
        json()
    }

    // Inicializar DB
    initDatabase()

    // Servicios
    val tokenService = TokenService(environment.config)
    val userRepository = UserRepository()
    val programRepository = ProgramRepository()
    
    val authService = AuthService(userRepository, tokenService)
    val programService = ProgramService(programRepository)

    // Configuración de Autenticación JWT
    install(Authentication) {
        jwt("auth-jwt") {
            realm = environment.config.property("jwt.realm").getString()
            verifier(tokenService.getVerifier())
            validate { credential ->
                if (credential.payload.getClaim("id").asInt() != null) {
                    JWTPrincipal(credential.payload)
                } else null
            }
        }
    }

    // Rutas
    routing {
        authRoutes(authService)
        programRoutes(programService)
    }
}

fun Application.initDatabase() {
    val driverClassName = environment.config.property("storage.driverClassName").getString()
    val jdbcURL = environment.config.property("storage.jdbcURL").getString()
    val user = environment.config.property("storage.user").getString()
    val password = environment.config.property("storage.password").getString()

    val hikariConfig = HikariConfig().apply {
        this.driverClassName = driverClassName
        this.jdbcUrl = jdbcURL
        this.username = user
        this.password = password
        this.maximumPoolSize = 3
        this.isReadOnly = false
        this.transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        this.validate()
    }

    val dataSource = HikariDataSource(hikariConfig)
    Database.connect(dataSource)

    transaction {
        SchemaUtils.create(UsersTable, ProgramsTable)
    }
}
