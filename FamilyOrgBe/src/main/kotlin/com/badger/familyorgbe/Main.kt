package com.badger.familyorgbe

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class MainApplication

fun main(args: Array<String>) {
    runApplication<MainApplication>(*args)
}


data class TestModel(val message: String)

@RestController
class FirstController {

    @GetMapping("/test")
    fun test(model: Model): TestModel {
        return TestModel(message = "Hello World!")
    }
}