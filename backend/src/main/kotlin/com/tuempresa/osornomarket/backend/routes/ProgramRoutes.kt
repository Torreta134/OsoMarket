package com.tuempresa.osornomarket.backend.routes

import com.tuempresa.osornomarket.backend.data.dto.CreateProgramRequest
import com.tuempresa.osornomarket.backend.service.NotFoundException
import com.tuempresa.osornomarket.backend.service.ProgramService
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.programRoutes(programService: ProgramService) {

    route("/programs") {
        
        get {
            val programs = programService.getAllPrograms()
            call.respond(programs)
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            try {
                val program = programService.getProgramById(id)
                call.respond(program)
            } catch (e: NotFoundException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
            }
        }

        // Rutas que requieren autenticación
        authenticate("auth-jwt") {
            
            post {
                val req = call.receive<CreateProgramRequest>()
                val id = programService.createProgram(req)
                call.respond(HttpStatusCode.Created, mapOf("id" to id))
            }

            put("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@put
                }
                try {
                    val req = call.receive<CreateProgramRequest>()
                    programService.updateProgram(id, req)
                    call.respond(HttpStatusCode.OK, "Programa actualizado")
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
                }
            }

            delete("/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "ID inválido")
                    return@delete
                }
                try {
                    programService.deleteProgram(id)
                    call.respond(HttpStatusCode.OK, "Programa eliminado")
                } catch (e: NotFoundException) {
                    call.respond(HttpStatusCode.NotFound, mapOf("error" to e.message))
                }
            }
        }
    }
}
