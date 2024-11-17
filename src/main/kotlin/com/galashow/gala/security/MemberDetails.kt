package com.galashow.gala.security

import com.galashow.gala.model.GalaUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.core.user.OAuth2User

class MemberDetails(
    private val galaUser: GalaUser
) : UserDetails ,OAuth2User{
    override fun getName(): String {
        return galaUser.userEmail
    }

    override fun getAttributes(): MutableMap<String, Any> {
        return mutableMapOf("role" to galaUser.role, "email" to galaUser.userEmail)
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(GrantedAuthority { galaUser.role })
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return galaUser.userEmail
    }

}