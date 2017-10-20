package migration.simple

import io.undertow.Undertow
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

class Application(port: Int = 8080, beanConfig: BeanDefinitionDsl = beansConfiguration()) {
    private val server: Undertow

    init {
        val context = GenericApplicationContext().apply {
            beanConfig.initialize(this)
            refresh()
        }
        val handler = WebHttpHandlerBuilder.applicationContext(context).build()
        val adapter = UndertowHttpHandlerAdapter(handler)
        server = Undertow.builder().addHttpListener(port, "localhost").setHandler(adapter).build()
    }

    fun start() {
        server.start()
    }

    fun startAndAwait() {
        server.start()
    }

    fun stop() {
        server.stop()
    }
}

fun main(args: Array<String>) {
    Application().startAndAwait()
}
