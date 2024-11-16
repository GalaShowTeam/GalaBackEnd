package com.galashow.gala

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GalaApplication

fun main(args: Array<String>) {
	runApplication<GalaApplication>(*args)
}
