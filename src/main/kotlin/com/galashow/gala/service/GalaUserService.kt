package com.galashow.gala.service

import com.galashow.gala.controller.GalaUserRequestDTO
import com.galashow.gala.exception.AccountNotMatchingException
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.model.entity.QGalaUser.galaUser
import com.galashow.gala.repository.GalaUserRepository
import com.galashow.gala.repository.GalaUserRepositoryCustom
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GalaUserService(
    private val galaUserRepository: GalaUserRepository
) {


    //유저 정보 update
    @Transactional
    fun updateGalaUserAttributes(galaUser:GalaUser,galaUserRequestDTO: GalaUserRequestDTO) : GalaUser{

        val userNickname : String? =  galaUserRequestDTO.userNickname?.also {
            galaUser.userNickname = it
        }
        val profileImage : String? = galaUserRequestDTO.profileImage?.also{
            galaUser.profileImg = it
        }
        val points: Long? = galaUserRequestDTO.points?.also{
            galaUser.points = (galaUser.points ?: 0 ) + it
        }

        galaUserRepository.updateGalaUserAttributes(galaUser,userNickname,profileImage,points)

        return galaUser
    }

    //유저 권한 체크
    fun isUserAuthorized(galaUser:GalaUser,userNo:Long) : Boolean{
        return galaUser.userNo == userNo
    }
}