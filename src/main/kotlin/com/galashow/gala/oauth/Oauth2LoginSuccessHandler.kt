package com.galashow.gala.oauth

import com.galashow.gala.jwt.JwtUtil
import com.galashow.gala.model.entity.CertList
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.repository.CertListRepository
import com.galashow.gala.security.MemberDetails
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import com.galashow.gala.util.Util
import net.minidev.json.JSONObject

@Component
class Oauth2LoginSuccessHandler(
    private val jwtUtil: JwtUtil,
    private val certListRepository: CertListRepository
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

        logger.info("로그인 성공 인증 저장")


        val certList  =  CertList(
            userNo = galaUser,
            deviceInfoCd = Util.extractDeviceInfo(request!!),
            ipAddress = request.remoteAddr,
            accessStatusCd = "SUC"
        )
        certListRepository.save(certList)

        response!!.status = HttpServletResponse.SC_OK
        response.contentType="application/json"
        response.characterEncoding="UTF8"

        val responseJson = Util.createResponse("SUCCESS","로그인에 성공했습니다.")

        responseJson["ACCESS_TOKEN"] = accessToken


        response.writer.write(responseJson.toJSONString())

        clearAuthenticationAttributes(request)
    }
}