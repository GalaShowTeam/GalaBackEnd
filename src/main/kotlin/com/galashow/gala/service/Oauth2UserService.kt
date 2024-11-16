package com.galashow.gala.service

import com.galashow.gala.model.BaseUser
import com.galashow.gala.model.GalaUser
import com.galashow.gala.repository.GalaUserRepository
import com.galashow.gala.security.MemberDetails
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service

@Service
class Oauth2UserService(
    private val galaUserRepository: GalaUserRepository
) : DefaultOAuth2UserService() {

    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val user = super.loadUser(userRequest)
        val attributes = user.attributes
        val provider = userRequest?.clientRegistration?.registrationId
        val email = attributes["email"] as String
        val name = attributes["name"] as String
        println("providerId: $provider");
        val providerCd = when(provider){
            "google" -> "001"
            "naver" -> "002"
            "kakao" -> "003"
            else -> "999"
        }

        val galaUser = galaUserRepository.findByUserEmail(email) ?:galaUserRepository.save(BaseUser(userNickname = name, provider = providerCd, userEmail = email))

        return MemberDetails(galaUser)
    }

}