package com.galashow.gala.util

import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

class Util {

  companion object {

      fun extractDeviceInfo(request: HttpServletRequest):String{

        val userAgent = request.getHeader("User-Agent")
        return when{
            userAgent.contains("Mobile", ignoreCase = true) -> "MOB"
            userAgent.contains("Tablet", ignoreCase = true) -> "TAB"
            userAgent.contains("Windows", ignoreCase = true) ||userAgent.contains("Macintosh", ignoreCase = true) -> "DES"
            else -> "999"

        }
    }

      fun extractFailReason(ex: Exception):String{
          return when(ex){
              is BadCredentialsException -> "001"
              is UsernameNotFoundException -> "002"
              else -> "999"
          }
      }
  }
}