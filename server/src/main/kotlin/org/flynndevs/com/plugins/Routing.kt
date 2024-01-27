package org.flynndevs.com.plugins

import Greeting
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.response.respond
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import kotlinx.serialization.Serializable

fun Application.configureRouting() {
    install (Routing) {
        get("/") {
            call.respondText("Ktor: ${Greeting().greet()}")
        }
        get("/users/{username}") {
            val user = call.parameters["username"]
            val header = call.request.headers["Connection"]
            call.respondText("Ktor: ${Greeting().greet()} $user header: $header")
        }
        get("/user") {
            val name = call.request.queryParameters["name"]
            val age = call.request.queryParameters["age"]
            call.respondText("Hi $name age $age")
        }
        get("/person"){
            try {
                val person = Person("Serchioo", 29)
                call.respond(message = person, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(message = "${e.message}", status = HttpStatusCode.BadRequest)
            }
        }
        get("/redirect") {
            call.respondRedirect(url = "/moved", permanent = false)
        }
        get("/moved") {
            call.respondText("Moved")
        }
    }
}

@Serializable
data class Person(val name: String, val age: Int)