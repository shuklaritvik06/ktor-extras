package com.example.plugins

import io.ktor.server.plugins.conditionalheaders.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.application.*
import java.io.File
import java.util.Date

fun Application.configureHTTP() {
    install(ConditionalHeaders){
        val file = File("src/main/kotlin/com/example/Application.kt")
        version { _, outgoingContent ->
            when (outgoingContent.contentType?.withoutParameters()) {
                ContentType.Text.CSS -> listOf(
                    EntityTagVersion(file.lastModified().hashCode().toString()),
                    LastModifiedVersion(Date(file.lastModified()))
                )
                else -> emptyList()
            }
        }
    }
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        anyHost()
    }
    install(DefaultHeaders) {
        header("X-Engine", "Ktor")
        header("X-Author","Ritvik Shukla")
    }
}
