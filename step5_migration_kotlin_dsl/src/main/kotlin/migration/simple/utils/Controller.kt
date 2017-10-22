package migration.simple.utils

import org.springframework.web.reactive.function.server.RouterFunctionDsl

interface Controller {
    fun nest(): RouterFunctionDsl.() -> Unit
}