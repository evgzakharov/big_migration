package migration.simple

import com.samskivert.mustache.Mustache
import migration.simple.config.DBConfiguration
import migration.simple.controller.StatsController
import migration.simple.controller.UserController
import migration.simple.repository.UserRepository
import migration.simple.service.StatsService
import migration.simple.view.MustacheResourceTemplateLoader
import migration.simple.view.MustacheViewResolver
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor
import org.springframework.context.annotation.ConfigurationClassPostProcessor
import org.springframework.context.support.BeanDefinitionDsl
import org.springframework.context.support.beans
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.RouterFunctions

fun beansConfiguration(beanConfig: BeanDefinitionDsl.() -> Unit = {}): BeanDefinitionDsl = beans {
    bean<DBConfiguration>()

    //controllers
    bean<StatsController>()
    bean<UserController>()

    //repository
    bean<UserRepository>()

    //services
    bean<StatsService>()

    //routes
    bean<Routes>()
    bean("webHandler") {
        RouterFunctions.toWebHandler(ref<Routes>().router(), HandlerStrategies.builder().viewResolver(ref()).build())
    }

    //view resolver
    bean {
        val prefix = "classpath:/templates/"
        val suffix = ".mustache"
        val loader = MustacheResourceTemplateLoader(prefix, suffix)
        MustacheViewResolver(Mustache.compiler().withLoader(loader)).apply {
            setPrefix(prefix)
            setSuffix(suffix)
        }
    }

    //processors
    bean<CommonAnnotationBeanPostProcessor>()
    bean<ConfigurationClassPostProcessor>()
    bean<ConfigurationPropertiesBindingPostProcessor>()

    //additional configuration
    beanConfig()
}

