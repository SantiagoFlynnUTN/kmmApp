package org.flynndevs.com

import SERVER_PORT
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import org.flynndevs.com.plugins.configureRouting

fun main() {
    embeddedServer(Netty, port = SERVER_PORT, host = "127.0.0.1") {
        install(ContentNegotiation) {
            json()
        }
        module()
        module2()
    }.start(wait = true)
}

fun Application.module() {
    configureRouting()
}

fun Application.module2() {
    routing {
        get("/book") {
            call.respondText("Ktor: 2")
        }
    }

}