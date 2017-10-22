package migration.simple.view

import com.samskivert.mustache.Mustache
import org.springframework.context.ResourceLoaderAware
import org.springframework.core.io.DefaultResourceLoader
import org.springframework.core.io.Resource
import org.springframework.core.io.ResourceLoader
import java.io.InputStreamReader
import java.io.Reader

/**
 * Mustache TemplateLoader implementation that uses a prefix, suffix and the Spring
 * Resource abstraction to load a template from a file, classpath, URL etc. A
 * [TemplateLoader] is needed in the [Compiler] when you want to render
 * partials (i.e. tiles-like features).
 * @author Dave Syer
 * @since 1.2.2
 * @see Mustache
 * @see Resource
 */
class MustacheResourceTemplateLoader(private val prefix: String, private val suffix: String) : Mustache.TemplateLoader, ResourceLoaderAware {

    private var charSet = "UTF-8"

    private var resourceLoader: ResourceLoader = DefaultResourceLoader()

    /**
     * Set the charset.
     * @param charSet the charset
     */
    fun setCharset(charSet: String) {
        this.charSet = charSet
    }

    /**
     * Set the resource loader.
     * @param resourceLoader the resource loader
     */
    override fun setResourceLoader(resourceLoader: ResourceLoader) {
        this.resourceLoader = resourceLoader
    }

    override fun getTemplate(name: String): Reader {
        return InputStreamReader(this.resourceLoader
                .getResource(this.prefix + name + this.suffix).inputStream,
                this.charSet)
    }

}