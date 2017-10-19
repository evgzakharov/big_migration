package migration.simple.controller

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import reactor.test.test

inline fun <reified T> WebClient.RequestHeadersSpec<*>.retrieveResultForTest(): StepVerifier.FirstStep<T> = this
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(T::class.java)
        .test()

fun WebClient.RequestBodySpec.postBody(body: Any): WebClient.RequestHeadersSpec<*> = this.body(BodyInserters.fromObject(body))
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)