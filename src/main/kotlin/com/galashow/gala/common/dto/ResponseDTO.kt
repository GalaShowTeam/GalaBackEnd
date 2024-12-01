package com.galashow.gala.common.dto

import com.galashow.gala.controller.GalaUserRequestDTO
import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.model.dto.GalaUserDTO
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.media.Schema

open class ContentResponse

@Schema(name = "ErrorDetails", description = "구체적 에러 메시지")
data class ErrorDetails(
    @field:Schema(description = "필드 이름")
    val fieldName:String,
    @field:Schema(description = "에러 메시지")
    val errorMessage:String,
) : ContentResponse()

@Schema(name = "ResponseDTO", description = "공통 응답 객체")
open  class ResponseDTO(
 val result : String,
 val msg: String,
)


@Schema(name = "ResponseDTOWithGalaUserDTO", description = "공통 응답 객체 - GalaUserDTO 포함")
class ResponseDTOWithGalaUserDTO(
   result: String,
   msg: String,
   val contents : GalaUserDTO
) : ResponseDTO(result,msg)

@Schema(name = "ResponseDTOWithContents", description = "공통 응답 객체 - WithContents")
class ResponseDTOWithContents(
    result: String,
    msg: String,
    @field:Schema(anyOf = [ErrorDetails::class,
                            GalaUserDTO::class,
                            BoardDTO::class
                            ]
    )
    val contents : Any
) : ResponseDTO(result,msg)

