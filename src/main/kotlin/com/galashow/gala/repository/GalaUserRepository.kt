package com.galashow.gala.repository

import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.model.entity.QGalaUser
import com.querydsl.core.QueryFactory
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
interface GalaUserRepository : JpaRepository<GalaUser, Long>,GalaUserRepositoryCustom {

    fun findByUserNo(userNo: Long): GalaUser?

    fun findByUserEmail(email: String): GalaUser?

    fun findByProviderId(providerId: String): GalaUser?
}

interface GalaUserRepositoryCustom{
    fun updateGalaUserAttributes (user: GalaUser,userNickname:String?,profileImg:String?,points:Long?)
}

class GalaUserRepositoryCustomImpl(
    private val queryFactory:JPAQueryFactory
): GalaUserRepositoryCustom{

    private val galaUser = QGalaUser.galaUser

    override fun updateGalaUserAttributes (user: GalaUser, userNickname: String?, profileImg: String?, points: Long?,) {
        val updateQuery = queryFactory.update(galaUser)

        if (!userNickname.isNullOrBlank()) {
            updateQuery.set(galaUser.userNickname, userNickname)
        }
        if (!profileImg.isNullOrBlank()) {
            updateQuery.set(galaUser.profileImg, profileImg)
        }
        if (points != null){
            updateQuery.set(galaUser.points, user.points)
        }

        updateQuery.where(galaUser.providerId.eq(user.providerId))

        updateQuery.execute()
    }
}