package com.galashow.gala.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

enum class ErrorCode(val statusCode: Int,val message:String){
    SERVER_ERROR(9999,"서버 에러"),
}

data class ErrorResponse(val code:ErrorCode,val message:String)

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    /* 유저 못 찾는 에러*/
    @ExceptionHandler(NoUserException::class)
    fun handleNoUserException(e: NoUserException): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(ErrorCode.SERVER_ERROR, message = e.message ?: "사용자를 찾을 수 없습니다.")
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(errorResponse)
    }
}