package migration.simple

import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.http.server.reactive.HttpHandler
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder
import reactor.ipc.netty.http.server.HttpServer
import reactor.ipc.netty.tcp.BlockingNettyContext

class Application(port: Int = 8080, beanDefinition: BeanDefinitionDsl = beans()) {
    private val httpHandler: HttpHandler
    private val server: HttpServer
    private var nettyContext: BlockingNettyContext? = null

    init {
        val context = GenericApplicationContext().apply {
            beanDefinition.initialize(this)
            refresh()
        }
        server = HttpServer.create(port)
        httpHandler = WebHttpHandlerBuilder.applicationContext(context).build()
    }

    fun start() {
        nettyContext = server.start(ReactorHttpHandlerAdapter(httpHandler))
    }

    fun startAndAwait() {
        server.startAndAwait(ReactorHttpHandlerAdapter(httpHandler)) {
            nettyContext = it
        }
    }

    fun stop() {
        nettyContext?.shutdown()
    }
}

fun main(args: Array<String>) {
    Application().startAndAwait()
}
