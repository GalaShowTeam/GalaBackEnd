package com.galashow.gala.jwt.filter

import com.galashow.gala.jwt.JwtUtil
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.common.util.Util
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter


@Component
class JwtRequestFilter(
    private val jwtUtil: JwtUtil
) :OncePerRequestFilter(){
    val TOKEN_PREFIX = "Bearer "

    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  filterChain: FilterChain
    ) {


        try{
            val accessToken = extractToken(request)

            if (!accessToken.isNullOrEmpty() && SecurityContextHolder.getContext().authentication == null) {
                val providerId = jwtUtil.getProviderId(accessToken)

                if (jwtUtil.validateToken(accessToken, providerId)) {
                    SecurityContextHolder.getContext().authentication = jwtUtil.getAuthentication(accessToken)
                }
            }
            
            filterChain.doFilter(request, response)
        }catch (e:Exception){
            handleAuthenticationFailure(response,e)
        }

    }

    //헤더에서 토큰 추출하기
    private fun extractToken(request:HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if(!header.isNullOrEmpty() && header.startsWith(TOKEN_PREFIX)){
            header.substring(TOKEN_PREFIX.length)
        }else{
            null
        }
    }

    //실패 response 보내기
    private fun handleAuthenticationFailure(response: HttpServletResponse, exception: Exception) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"

        val responseJson = Util.createResponse("ERROR","유효하지 않거나 비정상적인 토큰입니다.")
        response.writer.write(responseJson.toJSONString())
        response.flushBuffer()
        logger.error("JWT 인증 실패: ${exception.message}", exception)
    }
}