package com.galashow.gala

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class GalaApplication


fun main(args: Array<String>) {
	runApplication<GalaApplication>(*args)
	//TODO : 엔터티 생성과 더미 데이터 만들기
}
