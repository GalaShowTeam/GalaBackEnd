package com.galashow.gala.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FaviconController {

    // 브라우저가 자동으로 favicon.ico 을 요청해서 WARN 로그가 남길래 빈 응답을 보내주기로 함 
    @GetMapping("/favicon.ico")
    fun favicon(){
    }
}