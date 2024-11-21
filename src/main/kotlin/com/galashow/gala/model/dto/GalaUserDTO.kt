package com.galashow.gala.model.dto

import com.galashow.gala.model.entity.AdminUser
import com.galashow.gala.model.entity.BaseUser
import com.galashow.gala.model.entity.GalaUser

data class GalaUserDTO(
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