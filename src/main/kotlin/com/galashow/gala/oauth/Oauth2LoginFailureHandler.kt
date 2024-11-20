package com.galashow.gala.oauth

import com.galashow.gala.model.entity.CertList
import com.galashow.gala.repository.CertListRepository
import com.galashow.gala.util.Util
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.stereotype.Component


@Component
class Oauth2LoginFailureHandler(
    private val certListRepository: CertListRepository,
):SimpleUrlAuthenticationFailureHandler() {

    private val logger: Logger = LoggerFactory.getLogger(Oauth2LoginFailureHandler::class.java)

    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        exception: AuthenticationException?
    ) {
        logger.error("로그인 실패 정보 저장")


        val certList  =  CertList(
            deviceInfoCd = Util.extractDeviceInfo(request!!),
            ipAddress = request.remoteAddr,
            failReasonCd = Util.extractFailReason(exception!!),
            accessStatusCd = "002"
        )

        certListRepository.save(certList)

        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType="application/json"
        response.characterEncoding="UTF8"

        val errorResponse = mapOf(
            "ERROR" to "FORBIDDEN",
            "MSG" to "로그인 실패했습니다."
        )

        response.writer.write(JSONObject.toJSONString(errorResponse))
    }
}