package com.galashow.gala.model.dto

import com.galashow.gala.model.AdminUser
import com.galashow.gala.model.BaseUser
import com.galashow.gala.model.GalaUser
import jakarta.persistence.Column
import java.time.Instant

class GalaUserDTO(
    val userNo: Long?,

    val userEmail : String,

    var userNickname : String,

    var profileImg : String = "",

    var points : Long = 0,

    val role : String = "002"
) {
    companion object {
        fun toDto(entity : GalaUser) : GalaUserDTO{
            val type = when(entity){
                is AdminUser -> "001"
                is BaseUser -> "002"
                else -> "003"
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