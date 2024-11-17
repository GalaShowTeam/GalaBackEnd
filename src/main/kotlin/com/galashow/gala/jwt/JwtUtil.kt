package com.galashow.gala.jwt

import com.galashow.gala.security.MemberDetailsService
import io.jsonwebtoken.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SignatureException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import java.time.Instant
import java.util.*

import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${jwt.secret}") val SECRET_KEY: String,
    @Value("\${jwt.access-token.expiration-time}") val ACCESS_TOKEN_EXPIRY_TIME: Long,
    @Value("\${jwt.refresh-token.expiration-time}") val REFRESH_TOKEN_EXPIRY_TIME: Long,
    private val memberDetailsService: MemberDetailsService,

) {
    private val logger : Logger = LoggerFactory.getLogger(JwtUtil::class.java)

    private fun getSigningKey():SecretKey{
        val keyBytes :ByteArray = Decoders.BASE64.decode(SECRET_KEY)
        return Keys.hmacShaKeyFor(keyBytes)
    }

    //클레임 추출
    private fun getClaim(token:String) : Jws<Claims> {
        return try{
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token)
        }catch(e : ExpiredJwtException){
            throw ExpiredJwtException(null,null,"만료된 토큰입니다.")
        }
    }

    //액세스 토큰 발급
    fun generateAccessToken(username : String): String {
        val now = Instant.now()

        return Jwts.builder()
            .subject(username)
            .issuedAt(Date(now.toEpochMilli()))
            .expiration(Date(now.plusMillis(ACCESS_TOKEN_EXPIRY_TIME).toEpochMilli()))
            .signWith(getSigningKey())
            .compact()
    }

    //리프레쉬 토큰 발급
    fun generateRefreshToken(username : String): String {
        val now = Instant.now()

        return Jwts.builder()
            .subject(username)
            .issuedAt(Date(now.toEpochMilli()))
            .expiration(Date(now.plusMillis(REFRESH_TOKEN_EXPIRY_TIME).toEpochMilli()))
            .signWith(getSigningKey())
            .compact()
    }

    fun getUsername(token:String) : String{
        return getClaim(token).payload.subject
    }

    fun isTokenExpired(token:String):Boolean{
        val claims = getClaim(token)
        return claims.payload.expiration.before(Date())
    }

    fun validateToken(token:String,username: String):Boolean{
        return try{
            !isTokenExpired(token) && getClaim(token).payload.subject == username
        }catch (e: ExpiredJwtException) {
            logger.error("Token has expired")
            false
        } catch (e: MalformedJwtException) {
            logger.error("Invalid JWT token format")
            false
        } catch (e: SignatureException) {
            logger.error("JWT signature is invalid")
            false
        } catch (e: IllegalArgumentException) {
            logger.error("Token is null or empty")
            false
        }
    }

    fun getAuthentication(token: String): Authentication{
        val claims = getClaim(token)
        val userDetails = memberDetailsService.loadUserByUsername(claims.payload.subject)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

}