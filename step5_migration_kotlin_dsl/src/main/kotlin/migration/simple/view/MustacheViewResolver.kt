package migration.simple.view

import com.samskivert.mustache.Mustache
import com.samskivert.mustache.Mustache.Compiler
import org.springframework.web.reactive.result.view.AbstractUrlBasedView
import org.springframework.web.reactive.result.view.UrlBasedViewResolver

class MustacheViewResolver(private val compiler: Compiler = Mustache.compiler()) : UrlBasedViewResolver() {

    private var charset: String? = null

    init {
        viewClass = requiredViewClass()
    }

    /**
     * Set the charset.
     * @param charset the charset
     */
    fun setCharset(charset: String) {
        this.charset = charset
    }

    override fun requiredViewClass(): Class<*> {
        return MustacheView::class.java
    }

    override fun createView(viewName: String): AbstractUrlBasedView {
        val view = super.createView(viewName) as MustacheView
        view.setCompiler(this.compiler)
        this.charset?.let { view.setCharset(it) }
        return view
    }

}