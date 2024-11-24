package com.galashow.gala.model.dto

import com.galashow.gala.common.dto.ContentResponse
import com.galashow.gala.model.entity.AdminUser
import com.galashow.gala.model.entity.BaseUser
import com.galashow.gala.model.entity.GalaUser
import io.swagger.v3.oas.annotations.media.Schema

@Schema(name = "GalaUserDTO", description = "Gala User 응답 객체")
data class GalaUserDTO(
    val userNo: Long?,

    val userEmail : String,

    var userNickname : String,

    var profileImg : String = "",

    var points : Long = 0,

    val role : String = "USR"
) : ContentResponse() {
    companion object {
        fun toDto(entity : GalaUser) : GalaUserDTO{
            val type = when(entity){
                is AdminUser -> "ADM"
                is BaseUser -> "USR"
                else -> "UNK"
            }
            return GalaUserDTO(userNo = entity.userNo,
                                userEmail = entity.userEmail,
                                userNickname = entity.userNickname,
                                profileImg = entity.profileImg,
                                points = entity.points,
                                role = entity.role)
        }
    }
}