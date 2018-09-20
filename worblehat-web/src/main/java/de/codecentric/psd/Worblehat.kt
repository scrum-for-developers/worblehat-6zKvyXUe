package de.codecentric.psd

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.*

@SpringBootApplication
open class Worblehat {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            Worblehat().run(args)
        }
    }

    fun run(args: Array<String>) {
        val scan = Scanner(System.`in`)
        val applicationContext = SpringApplication.run(Worblehat::class.java, *args)

        var line: String
        do {
            line = scan.nextLine()
        } while ("stop" != line)
        applicationContext.close()
    }
}
