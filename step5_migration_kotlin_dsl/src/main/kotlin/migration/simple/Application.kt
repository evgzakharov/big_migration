package migration.simple

import io.undertow.Undertow
import org.springframework.boot.env.YamlPropertySourceLoader
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.GenericApplicationContext
import org.springframework.core.io.ClassPathResource
import org.springframework.http.server.reactive.UndertowHttpHandlerAdapter
import org.springframework.web.server.adapter.WebHttpHandlerBuilder

class Application(port: Int? = null, beanConfig: BeanDefinitionDsl = beansConfiguration()) {
    private val server: Undertow

    init {
        val context = GenericApplicationContext().apply {
            beanConfig.initialize(this)
            loadConfig()

            refresh()
        }

        val adapter = WebHttpHandlerBuilder.applicationContext(context).build()
                .run { UndertowHttpHandlerAdapter(this) }

        val startupPort = port
                ?: context.environment.getProperty("server.port")?.toInt()
                ?: DEFAULT_PORT

        server = Undertow.builder()
                .addHttpListener(startupPort, "localhost")
                .setHandler(adapter)
                .build()
    }

    fun start() {
        server.start()
    }

    fun stop() {
        server.stop()
    }

    private fun GenericApplicationContext.loadConfig() {
        val resource = ClassPathResource("/application.yml")
        val sourceLoader = YamlPropertySourceLoader()
        val properties = sourceLoader.load("main config", resource, null)
        environment.propertySources.addFirst(properties)
    }

    companion object {
        private val DEFAULT_PORT = 8080
    }
}

fun main(args: Array<String>) {
    Application().start()
}
