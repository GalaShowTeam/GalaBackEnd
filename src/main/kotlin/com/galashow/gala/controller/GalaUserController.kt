package com.galashow.gala.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class GalaUserController {

    @RequestMapping("/get")
    fun getUser(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World")
    }

}