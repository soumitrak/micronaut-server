package sk.test.app

import io.micronaut.runtime.Micronaut

object Application {

    @JvmStatic
    fun main(args: Array<String>) {
        Micronaut.build()
                .packages("sk.test")
                .mainClass(Application.javaClass)
                .start()
    }
}
