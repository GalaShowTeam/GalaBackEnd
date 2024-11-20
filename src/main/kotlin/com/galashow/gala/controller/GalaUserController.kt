package com.galashow.gala.controller

import com.galashow.gala.model.dto.GalaUserDTO
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.security.MemberDetails
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController



@RestController
@RequestMapping("/user")
class GalaUserController {

    @RequestMapping("/{userNo}")
    fun getUser(authentication: Authentication,@PathVariable("userNo") userNo : Long): ResponseEntity<Any> {

        val loginUser = authentication.principal as? MemberDetails
                                            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        return if(loginUser.getUserNo() == userNo){
            ResponseEntity.ok(GalaUserDTO.toDto(loginUser.getGalaUser()))
        }else{
            //TODO : util 에 JSON 만드는 코드 짜기
            ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.")
        }
    }

}