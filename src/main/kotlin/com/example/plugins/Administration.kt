package com.example.plugins

import io.ktor.server.engine.*
import io.ktor.server.application.*

fun Application.configureAdministration() {
    install(ShutDownUrl.ApplicationCallPlugin) {
        shutDownUrl = "/ktor/shutdown"
        exitCodeSupplier = { 0 }
    }
}
