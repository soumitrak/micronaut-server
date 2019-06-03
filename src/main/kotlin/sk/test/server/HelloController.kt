package sk.test.server

import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Put
import reactor.core.publisher.Mono

@Controller("/")
class HelloController {

    @Get("test-mono/{id}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun test_mono(id: String): Mono<String> {
        Thread.sleep(4000)
        return Mono.just("Hello World! $id")
    }

    @Get("test-no-mono/{id}", produces = arrayOf(MediaType.APPLICATION_JSON))
    fun test_no_mono(id: String): String {
        Thread.sleep(4000)
        return "Hello World! $id"
    }
}
