package sk.test.server

import io.micronaut.context.ApplicationContext
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.spek.api.Spek
import org.junit.jupiter.api.Assertions.assertEquals
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

object HelloControllerSpec : Spek({
    lateinit var server: EmbeddedServer
    lateinit var client: HttpClient

    val LOG = LoggerFactory.getLogger(HelloControllerSpec::class.java)

    beforeGroup {
        server = ApplicationContext.run(EmbeddedServer::class.java)
        client = server
                .getApplicationContext()
                .createBean(HttpClient::class.java, server.getURL())
    }

    group("Test HelloController") {
        test("using Mono") {
            LOG.info("Tests using mono")

            val executor = Executors.newFixedThreadPool(100)
            for (i in 1..1000) {
                val worker = Runnable {
                    assertEquals("Hello World! $i", client.toBlocking().retrieve("/test-mono/$i"))
                }
                executor.execute(worker)
            }
            executor.shutdown()
            while (!executor.isTerminated) {
            }

            LOG.info("Done tests using mono")
        }

        test("without Mono") {
            LOG.info("Tests without mono")

            val executor = Executors.newFixedThreadPool(100)
            for (i in 1..1000) {
                val worker = Runnable {
                    assertEquals("Hello World! $i", client.toBlocking().retrieve("/test-no-mono/$i"))
                }
                executor.execute(worker)
            }
            executor.shutdown()
            while (!executor.isTerminated) {
            }

            LOG.info("Done tests without mono")
        }
    }

    afterGroup {
        client.stop()
        server.stop()
    }
})
