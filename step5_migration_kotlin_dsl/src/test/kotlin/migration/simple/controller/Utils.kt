package migration.simple.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import okhttp3.*

var client = OkHttpClient()

val JSON = MediaType.parse("application/json; charset=utf-8")

val mapper: ObjectMapper = ObjectMapper()
        .registerKotlinModule()

inline fun <reified T> String.GET(): T {
    val request = Request.Builder()
            .url(this)
            .build()

    return client.newCall(request).executeAndGet(T::class.java)
}

inline fun <reified T> String.PUT(data: Any): T {
    val body = RequestBody.create(JSON, mapper.writeValueAsString(data))
    val request = Request.Builder()
            .url(this)
            .put(body)
            .build()

    return client.newCall(request).executeAndGet(T::class.java)
}

inline fun <reified T> String.POST(data: Any): T {
    val body = RequestBody.create(JSON, mapper.writeValueAsString(data))
    val request = Request.Builder()
            .url(this)
            .post(body)
            .build()

    return client.newCall(request).executeAndGet(T::class.java)
}

inline fun <reified T> String.DELETE(): T {
    val request = Request.Builder()
            .url(this)
            .delete()
            .build()

    return client.newCall(request).executeAndGet(T::class.java)
}

fun <T> Call.executeAndGet(clazz: Class<T>): T {
    execute().use { response ->
        return mapper.readValue(response.body()!!.string(), clazz)
    }
}
