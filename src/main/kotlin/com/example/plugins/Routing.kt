package com.example.plugins

import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.webjars.*
import io.ktor.http.*
import io.ktor.server.http.content.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.application.*
import io.ktor.server.html.*
import kotlinx.css.*
import kotlinx.html.body
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.link
import java.io.File

suspend inline fun ApplicationCall.respondCss(builder: CssBuilder.() -> Unit) {
    this.respondText(CssBuilder().apply(builder).toString(), ContentType.Text.CSS)
}


fun Application.configureRouting() {
    install(Webjars) {
        path = "/webjars"
    }
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, status ->
            call.respondText(text = "404: Page Not Found", status = status)
        }
    }
    install(AutoHeadResponse)
    routing {
            get("/html") {
                call.respondHtml {
                    head {
                        link(rel = "stylesheet", href = "/styles.css", type = "text/css")
                    }
                    body {
                        h1(classes = "title,bigWithBg") {
                            +"Hello from My Ktor App!"
                        }
                    }
                }
            }
            get("/styles.css") {
                call.respondCss {
                     body {
                        backgroundColor = Color.yellow
                        margin(0.px)
                        padding(0.px)
                    }
                    rule("h1.title") {
                        color = Color.white
                    }
                    rule("h1.bigWithBg"){
                        fontSize = (20.px)
                        backgroundColor = Color.aqua
                    }
                }
            }
        get("/") {
            call.respondText("Hello I am OK!")
        }
        get("/webjars") {
            call.respondText("<script src='/webjars/jquery/jquery.js'></script>", ContentType.Text.Html)
        }
        staticFiles("/static", File("src/main/resources/static")) {
            staticResources("static","")
        }
    }
}
