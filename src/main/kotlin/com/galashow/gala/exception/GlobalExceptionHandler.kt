package com.galashow.gala.exception

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException
import com.galashow.gala.common.dto.ErrorDetails
import com.galashow.gala.common.dto.ResponseDTO
import com.galashow.gala.common.dto.ResponseDTOWithContents
import com.galashow.gala.common.util.Util
import org.apache.coyote.Response
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler





@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    /* 유저 못 찾는 에러*/
    @ExceptionHandler(NoUserException::class)
    fun handleNoUserException(e: NoUserException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ResponseDTO("FAIL",e.message!!))
    }

    /* 요청 파라미터 에러*/
    @ExceptionHandler(BadArgumentException::class)
    fun handleBadArgumentException(e: BadArgumentException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Util.createResponse("FAIL",e.message!!))
    }

    /* Validate 에러*/
    override fun handleMethodArgumentNotValid(
        e: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errorMessages :MutableList<ErrorDetails> = mutableListOf<ErrorDetails>()
        e.bindingResult.allErrors.forEach { error ->
            if (error is FieldError) {
                val map = mutableMapOf<String,String>()
                val fieldName = error.field
                val errorMessage = error.defaultMessage ?: "유효하지 않은 값입니다."
                errorMessages.add(ErrorDetails(fieldName = fieldName,errorMessage = errorMessage))
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTOWithContents("FAIL","요청 데이터 검증 실패",errorMessages))
    }

    /* 인증 실패 에러*/
    @ExceptionHandler(AccountNotMatchingException::class)
    fun handleAccountNotMatchingException(e: AccountNotMatchingException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ResponseDTO("FAIL",e.message!!))
    }

    /* 잘못된 요청 객체 에러*/
    @ExceptionHandler(UnrecognizedPropertyException::class)
    fun handleUnrecognizedPropertyException(e: UnrecognizedPropertyException): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO("FAIL",e.message!!))
    }

    /* 잘못된 요청 객체 에러*/
    override fun handleHttpMessageNotReadable(
        e: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseDTO("FAIL","잘못된 요청입니다. JSON 형식이 올바르지 않거나, 허용되지 않은 필드가 포함되어 있습니다."))
    }

    @ExceptionHandler(NotFoundException::class)
    fun noContentException(e: NotFoundException) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseDTO("NOT_FOUND","해당하는 리소스가 없습니다."))
    }
}