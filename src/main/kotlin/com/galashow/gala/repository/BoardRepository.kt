package com.galashow.gala.repository

import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.model.entity.Board
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.model.entity.QBoard
import com.galashow.gala.model.entity.QComCdI
import com.galashow.gala.model.entity.QComCdIId
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BoardRepository : JpaRepository<Board,Long> , BoardRepositoryCustom{


}

interface BoardRepositoryCustom {
    fun findAllBoardDTO() : MutableList<BoardDTO>
    fun findBoardDTOByBoardNo(boardNo : Long) : BoardDTO?
}

class BoardRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): BoardRepositoryCustom{

    private val board = QBoard.board

    val categoryCdI = QComCdI("categoryCdI")

    private val versusStatusCdI = QComCdI("versusStatusCdI")

    override fun findBoardDTOByBoardNo(boardNo : Long): BoardDTO?{
        return queryFactory
            .select(
                Projections.constructor(BoardDTO::class.java,
                board.boardNo,
                board.boardTitle,
                board.boardContent,
                board.versusSideA,
                board.versusSideB,
                board.userNo.userNickname.`as`("userNickname"),
                categoryCdI.cdNm.`as`("category"),
                board.likeCount,
                versusStatusCdI.cdNm.`as`("versusStatus"),
                board.crtDt,
                board.endDate)
            )
            .from(board)
            .innerJoin(categoryCdI)
            .on(board.categoryCd.eq(categoryCdI.id.cdValue)
                .and(categoryCdI.id.cdGroup.eq("category")))
            .innerJoin(versusStatusCdI)
            .on(board.versusStatus.eq(versusStatusCdI.id.cdValue)
                .and(versusStatusCdI.id.cdGroup.eq("versusStatus")))
            .where(board.boardNo.eq(boardNo))
            .fetchOne()
    }

    override fun findAllBoardDTO(): MutableList<BoardDTO> {
        return queryFactory
            .select(
                Projections.constructor(BoardDTO::class.java,
                    board.boardNo,
                    board.boardTitle,
                    board.boardContent,
                    board.versusSideA,
                    board.versusSideB,
                    board.userNo.userNickname.`as`("userNickname"),
                    categoryCdI.cdNm.`as`("category"),
                    board.likeCount,
                    versusStatusCdI.cdNm.`as`("versusStatus"),
                    board.crtDt,
                    board.endDate)
            )
            .from(board)
            .innerJoin(categoryCdI)
            .on(board.categoryCd.eq(categoryCdI.id.cdValue)
                .and(categoryCdI.id.cdGroup.eq("category")))
            .innerJoin(versusStatusCdI)
            .on(board.versusStatus.eq(versusStatusCdI.id.cdValue)
                .and(versusStatusCdI.id.cdGroup.eq("versusStatus")))
            .fetch()
    }

}