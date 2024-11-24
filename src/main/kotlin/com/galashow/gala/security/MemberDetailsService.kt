package com.galashow.gala.security

import com.galashow.gala.exception.NoUserException
import com.galashow.gala.repository.GalaUserRepository
import jakarta.persistence.EntityManager
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberDetailsService(
    private val galaUserRepository: GalaUserRepository,
    private val entityManager: EntityManager,
) : UserDetailsService{
    override fun loadUserByUsername(providerId: String?): UserDetails {
        if(providerId.isNullOrEmpty()){
            throw IllegalArgumentException("providerId cannot be empty")
        }
        val user = galaUserRepository.findByProviderId(providerId) ?: throw NoUserException("해댕하는 사용자가 없습니다.")

        return MemberDetails(user)
    }


}