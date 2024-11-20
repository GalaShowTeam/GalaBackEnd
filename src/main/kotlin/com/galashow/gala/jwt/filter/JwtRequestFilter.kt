package com.galashow.gala.jwt.filter

import com.galashow.gala.jwt.JwtUtil
import com.galashow.gala.security.MemberDetails
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

    private fun extractToken(request:HttpServletRequest): String? {
        val header = request.getHeader(HttpHeaders.AUTHORIZATION)
        return if(!header.isNullOrEmpty() && header.startsWith(TOKEN_PREFIX)){
            header.substring(TOKEN_PREFIX.length)
        }else{
            null
        }
    }

    private fun handleAuthenticationFailure(response: HttpServletResponse, exception: Exception) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        response.writer.write("{\"ERROR\": {\"Invalid or Malformed Token\"}")
        response.flushBuffer()
        logger.error("JWT 인증 실패: ${exception.message}", exception)
    }
}