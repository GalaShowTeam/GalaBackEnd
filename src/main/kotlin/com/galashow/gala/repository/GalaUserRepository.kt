package com.galashow.gala.repository

import com.galashow.gala.model.GalaUser
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GalaUserRepository : JpaRepository<GalaUser, Long> {

    fun findByUserNo(userNo: Long): GalaUser?
}