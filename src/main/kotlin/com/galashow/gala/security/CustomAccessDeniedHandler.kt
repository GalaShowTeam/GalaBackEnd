package com.galashow.gala.security

import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.galashow.gala.util.Util
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import net.minidev.json.JSONObject
import net.minidev.json.JSONUtil
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class CustomAccessDeniedHandler : AccessDeniedHandler{
    override fun handle(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        accessDeniedException: AccessDeniedException?
    ) {
        response!!.status = HttpServletResponse.SC_FORBIDDEN
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val responseJson = Util.createResponse("FORBIDDEN","권한이 없습니다.")


        response.writer.write(responseJson.toJSONString())
    }

}