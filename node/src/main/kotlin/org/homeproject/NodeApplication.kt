package org.homeproject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
open class NodeApplication

fun main(args: Array<String>) {
    runApplication<NodeApplication>(*args)
}
