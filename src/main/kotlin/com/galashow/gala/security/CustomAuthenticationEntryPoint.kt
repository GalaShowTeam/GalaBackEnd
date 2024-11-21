package com.galashow.gala.security

import com.galashow.gala.util.Util
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONObject
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {

    val logger : Logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint::class.java)

    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType="application/json"
        response.characterEncoding="UTF8"
        logger.error(authException?.message, authException)

        val responseJson = Util.createResponse("FORBIDDEN","인증에 실패했습니다.")


        response.writer.write(responseJson.toJSONString())

    }
}