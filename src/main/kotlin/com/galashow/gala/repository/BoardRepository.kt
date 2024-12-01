package com.galashow.gala.repository

import com.galashow.gala.model.entity.Board
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.model.entity.QBoard
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BoardRepository : JpaRepository<Board,Long> , BoardRepositoryCustom{


}

interface BoardRepositoryCustom {
}

class BoardRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): BoardRepositoryCustom{

    private val board = QBoard.board



}