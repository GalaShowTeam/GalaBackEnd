package com.galashow.gala.common.util

import jakarta.servlet.http.HttpServletRequest
import net.minidev.json.JSONObject
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException

class Util {

  companion object {

      fun extractDeviceInfo(request: HttpServletRequest):String{

        val userAgent = request.getHeader("User-Agent")
        return when{
            userAgent.contains("Mobile", ignoreCase = true) -> "MBL"
            userAgent.contains("Tablet", ignoreCase = true) -> "TBL"
            userAgent.contains("Windows", ignoreCase = true) ||userAgent.contains("Macintosh", ignoreCase = true) -> "DSK"
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

      fun createResponse(responseResult : String, msg:String) :JSONObject{
          return JSONObject(LinkedHashMap<String,Any>().apply { put("RESULT",responseResult)
                                                                put("MSG",msg)})
      }

      fun createResponse(responseResult: String,msg:String, mapObjects : Any):JSONObject{
          return JSONObject(LinkedHashMap<String,Any>().apply { put("RESULT",responseResult)
                                                                put("MSG",msg)
                                                                put("contents",mapObjects)})
      }
  }
}