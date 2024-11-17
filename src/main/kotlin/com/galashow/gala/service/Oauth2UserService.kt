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
        println(attributes)
        val provider = userRequest?.clientRegistration?.registrationId
        lateinit var providerCd:String
        lateinit var email:String
        lateinit var providerId:String
        when(provider){
            "google" -> {
                providerCd = "001"
                email = attributes["email"] as String
                providerId = attributes["sub"] as String
            }
            "kakao" -> {
                providerCd = "002"
                val kakaoAcount = attributes["kakao_account"] as? Map<String,Any>
                email = kakaoAcount?.get("email") as String
                providerId = attributes["id"].toString()
            }
            "naver" -> {
                providerCd = "003"
                val response = attributes["response"] as? Map<String,Any>
                email = response?.get("email") as String
                providerId = response?.get("id") as String
            }
        }


        val galaUser = galaUserRepository.findByProviderId(providerId) ?:galaUserRepository.save(BaseUser( provider = providerCd, userEmail = email, providerId = providerId))

        return MemberDetails(galaUser)
    }

}