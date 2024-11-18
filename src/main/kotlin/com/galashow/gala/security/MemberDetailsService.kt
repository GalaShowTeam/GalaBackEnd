package com.galashow.gala.security

import com.galashow.gala.exception.NoUserException
import com.galashow.gala.repository.GalaUserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class MemberDetailsService(
    private val galaUserRepository: GalaUserRepository
) : UserDetailsService{
    override fun loadUserByUsername(providerId: String?): UserDetails {
        if(providerId.isNullOrEmpty()){
            throw IllegalArgumentException("providerId cannot be empty")
        }
        val user = galaUserRepository.findByProviderId(providerId) ?: throw NoUserException()

        return MemberDetails(user)
    }


}