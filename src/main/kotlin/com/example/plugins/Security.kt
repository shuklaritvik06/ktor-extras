package com.example.plugins

import io.ktor.server.auth.*
import io.ktor.server.auth.ldap.*
import io.ktor.server.application.*

fun Application.configureSecurity() {
    val localhost = "http://0.0.0.0"
        val ldapServerPort = 6998 // TODO: change to real value!
        authentication {
        basic("auth-ldap") {
            realm = "realm"
            validate { credential ->
                ldapAuthenticate(credential, "ldap://$localhost:${ldapServerPort}", "uid=%s,ou=system"){
                    if (it.name == it.password) {
                        UserIdPrincipal(it.name)
                    } else {
                        null
                    }
                }
            }
        }
    }
}
