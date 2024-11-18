package com.galashow.gala.oauth

import com.galashow.gala.jwt.JwtUtil
import com.galashow.gala.model.GalaUser
import com.galashow.gala.security.MemberDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component

@Component
class Oauth2LoginSuccessHandler(
    private val jwtUtil: JwtUtil,
) : SimpleUrlAuthenticationSuccessHandler() {

    private val logger : Logger = LoggerFactory.getLogger(Oauth2LoginSuccessHandler::class.java)

    @Override
    override fun onAuthenticationSuccess(request: HttpServletRequest?, response: HttpServletResponse?, authentication: Authentication?) {
        val token : OAuth2AuthenticationToken = authentication as OAuth2AuthenticationToken


        val memberDetails : MemberDetails = token.principal as MemberDetails

        val galaUser : GalaUser = memberDetails.getGalaUser()
        logger.info("princial : ${memberDetails.getProviderId()}")


        val accessToken : String = jwtUtil.generateAccessToken(galaUser)

        logger.info(accessToken)

        val tokenMember = jwtUtil.getAuthentication(accessToken).principal as MemberDetails
        
        //TODO : Oauth2LoginFailHandler 작성
    }
}