package com.galashow.gala.controller

import com.galashow.gala.common.dto.ResponseDTOWithContents
import com.galashow.gala.exception.AccountNotMatchingException
import com.galashow.gala.model.dto.GalaUserDTO
import com.galashow.gala.repository.GalaUserRepository
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.service.GalaUserService
import com.galashow.gala.common.util.Util
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

data class GalaUserRequestDTO(
    @field:Size(max = 10, message = "닉네임의 글자 크기는 최대 10입니다.")
    val userNickname: String ? = null,
    val profileImage : String ? = null,
    val points : Long ? = null
)

@RestController
@RequestMapping("/user")
class GalaUserController(private val galaUserRepository: GalaUserRepository,private val galaUserService: GalaUserService) {

    private val logger : Logger = LoggerFactory.getLogger(GalaUserController::class.java)

    @Operation(
        summary = "사용자 정보 조회",
        description = "사용자 번호에 해당하는 사용자 정보를 반환합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 정보 반환 성공",
                content = [Content(mediaType = "application/json",
                    schema = Schema(implementation = GalaUserDTO::class),
                    examples = [ExampleObject(name = "GalaUserDtoExample",ref = "#/components/examples/GalaUserDtoExample")])]
            )
        ]
    )
    @GetMapping("/{userNo}")
    fun getUser(authentication: Authentication,@PathVariable("userNo") userNo : Long): ResponseEntity<Any> {

        val loginUser = authentication.principal as? MemberDetails
                                            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        val loginGalaUser = loginUser.getGalaUser()

        if(!galaUserService.isUserAuthorized(loginGalaUser,userNo)){
            throw AccountNotMatchingException("접근 권한이 없습니다.")
        }

        return ResponseEntity.ok(ResponseDTOWithContents(result = "SUCCESS", msg = "성공했습니다.", contents = GalaUserDTO.toDto(loginGalaUser)))
    }

    @Operation(
        summary = "사용자 정보 업데이트",
        description = "사용자 번호에 해당하는 사용자 정보(닉네임, 프로필이미지, 점수[+ 합산 형식])을 업데이트 한 후, 업데이트한 유저 정보를 반환합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "사용자 정보 업데이트 성공",
                content = [Content(mediaType = "application/json",
                        schema = Schema(implementation = GalaUserDTO::class),
                        examples = [ExampleObject(name = "GalaUserDtoExample", ref = "#/components/examples/GalaUserDtoExample")])]
            )
        ]
    )
    @PatchMapping("/{userNo}")
    fun updateUser(@PathVariable("userNo") userNo: Long,
                   @RequestBody @Validated galaUserRequestDTO: GalaUserRequestDTO,
                   authentication: Authentication) : ResponseEntity<Any> {

        val loginUser = authentication.principal as? MemberDetails ?: return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
            Util.createResponse("FAIL","적절하지 않은 사용자입니다."))

        val loginGalaUser = loginUser.getGalaUser()

        if(!galaUserService.isUserAuthorized(loginGalaUser,userNo)){
            throw AccountNotMatchingException("접근 권한이 없습니다.")
        }
        try{
            val updatedUser = galaUserService.updateGalaUserAttributes(loginGalaUser,galaUserRequestDTO)
            return ResponseEntity.status(HttpStatus.OK).body(Util.createResponse("SUCCESS","업데이트에 성공했습니다.",GalaUserDTO.toDto(updatedUser)))
        }catch (e:Exception){
            logger.error(e.message, e)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Util.createResponse("FAIL","내부 서버 에러 발생"))
        }
    }
}

