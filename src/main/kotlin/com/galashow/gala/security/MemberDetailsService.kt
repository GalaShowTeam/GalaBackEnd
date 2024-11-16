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
    override fun loadUserByUsername(email: String?): UserDetails {
        if(email.isNullOrEmpty()){
            throw IllegalArgumentException("Email cannot be empty")
        }
        val user = galaUserRepository.findByUserEmail(email) ?: throw NoUserException()

        return MemberDetails(user)
    }


}