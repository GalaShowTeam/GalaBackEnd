package com.galashow.gala.controller

import com.galashow.gala.model.dto.GalaUserDTO
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.util.Util
import com.galashow.gala.util.Util.Companion.createResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class GalaUserController {

    @GetMapping("/{userNo}")
    fun getUser(authentication: Authentication,@PathVariable("userNo") userNo : Long): ResponseEntity<Any> {

        val loginUser = authentication.principal as? MemberDetails
                                            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        return if(loginUser.getUserNo() == userNo){
            ResponseEntity.ok(Util.createResponse("SUCCESS","성공했습니다.", GalaUserDTO.toDto(loginUser.getGalaUser())))
        }else{
            ResponseEntity.status(HttpStatus.FORBIDDEN).body(Util.createResponse("FAIL","접근 권한이 없습니다."))
        }
    }

}