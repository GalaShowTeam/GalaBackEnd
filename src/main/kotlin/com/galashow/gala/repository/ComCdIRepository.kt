package com.galashow.gala.repository

import com.galashow.gala.model.entity.ComCdI
import com.galashow.gala.model.entity.ComCdIId
import com.galashow.gala.model.entity.QComCdI
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComCdIRepository : JpaRepository<ComCdI,ComCdIId>{
}

interface ComCdIRepositoryCustom {
    fun findCdNmByCdGroupAndCdValue(cdGroup : String,cdValue : String) : String?
}

class ComCdIRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
) : ComCdIRepositoryCustom{
    val comCdI = QComCdI.comCdI


    override fun findCdNmByCdGroupAndCdValue(cdGroup: String, cdValue: String): String? {
        return queryFactory
            .select(comCdI.cdNm)
            .from(comCdI)
            .where(comCdI.id.cdGroup.eq(cdGroup)
                .and(comCdI.id.cdValue.eq(cdValue)))
            .fetchOne()
    }
}