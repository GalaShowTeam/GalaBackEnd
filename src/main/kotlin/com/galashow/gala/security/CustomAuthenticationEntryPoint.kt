package com.galashow.gala.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONObject
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationEntryPoint: AuthenticationEntryPoint {
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        authException: AuthenticationException?
    ) {
        response!!.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType="application/json"
        response.characterEncoding="UTF8"
        println(authException)
        val errorResponse = mapOf(
            "ERROR" to "FORBIDDEN",
            "MSG" to "인증에 실패했습니다."
        )

        response.writer.write(JSONObject.toJSONString(errorResponse))

    }
}