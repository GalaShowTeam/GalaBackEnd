package com.galashow.gala.controller

import com.galashow.gala.model.dto.GalaUserDTO
import com.galashow.gala.repository.GalaUserRepository
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.util.Util
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/user")
class GalaUserController(private val galaUserRepository: GalaUserRepository) {

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

    @PatchMapping("/{userNo}")
    @Transactional
    fun updateUser(@RequestBody galaUserRequestDTO: GalaUserRequestDTO,authentication: Authentication) : ResponseEntity<Any> {
        val loginUser = authentication.principal as? MemberDetails ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Util.createResponse("FAIL","적절하지 않은 사용자입니다."))

        val loginGalaUser = loginUser.getGalaUser()
        try{
            //동적쿼리 공부하기
            galaUserRequestDTO.userNickname?.let { it -> loginGalaUser.userNickname = it }
            galaUserRequestDTO.points?.let {it -> loginGalaUser.points = it}
            galaUserRequestDTO.profileImage?.let{it -> loginGalaUser.profileImg = it}

            return ResponseEntity.status(HttpStatus.OK).body(Util.createResponse("SUCCESS","업데이트에 성공했습니다.",GalaUserDTO.toDto(loginGalaUser)))
        }catch (e:Exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Util.createResponse("FAIL","내부 서버 에러 발생"))
        }

    }


}

data class GalaUserRequestDTO(
    val userNickname: String ? = null,
    val profileImage : String ? = null,
    val points : Long ? = null
)