package com.example.demo.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import com.example.demo.model.UserRequest
import com.example.demo.repository.UserRepository
import kotlinx.serialization.json.Json

fun Route.userRoutes(repository: UserRepository) {

    route("/users") {

        // GET /users
        get {
            call.respond(repository.getAll())
        }

        // GET /users/{id}
        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@get
            }

            val user = repository.getById(id)
            if (user == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(user)
            }
        }

        // POST /users
        post {
            // 👇 อ่าน raw body + decode เอง
            val raw = call.receiveText()
            val request = Json.decodeFromString<UserRequest>(raw)

            val created = repository.create(request.name, request.email)
            call.respond(HttpStatusCode.Created, created)
        }

        // PUT /users/{id}
        put("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@put
            }

            val request = call.receive<UserRequest>()
            val updated = repository.update(id, request.name, request.email)

            if (updated == null) {
                call.respond(HttpStatusCode.NotFound)
            } else {
                call.respond(updated)
            }
        }

        // DELETE /users/{id}
        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid id")
                return@delete
            }

            val deleted = repository.delete(id)
            if (deleted) {
                call.respond(HttpStatusCode.NoContent)
            } else {
                call.respond(HttpStatusCode.NotFound)
            }
        }
    }
}
