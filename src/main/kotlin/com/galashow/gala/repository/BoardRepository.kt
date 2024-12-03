package com.galashow.gala.repository

import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.model.entity.Board
import com.galashow.gala.model.entity.QBoard
import com.galashow.gala.model.entity.QComCdI
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board,Long> , BoardRepositoryCustom{


}

interface BoardRepositoryCustom {
    fun findAllBoardDTO(pageable:Pageable,sort: String) : Page<BoardDTO>
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
                Expressions.stringTemplate("gala.fn_code_nm({0},{1})",
                                            Expressions.constant("category"),board.categoryCd).`as`("category"),
                board.likeCount,
                Expressions.stringTemplate("gala.fn_code_nm({0},{1})",
                    Expressions.constant("versusStatus"),board.versusStatus).`as`("versusStatus"),
                board.crtDt,
                board.endDate)
            )
            .from(board)
            .where(board.boardNo.eq(boardNo))
            .fetchOne()
    }

    override fun findAllBoardDTO(pageable: Pageable,sort:String): Page<BoardDTO> {
        val orderSpecifier : OrderSpecifier<*> = when(sort){
            "likeCount" -> board.likeCount.desc()
            else -> board.crtDt.desc()
        }
        val total = queryFactory
            .select(board.count())
            .from(board)
            .fetchOne() ?: 0L

        val conent = queryFactory
            .select(
                Projections.constructor(BoardDTO::class.java,
                    board.boardNo,
                    board.boardTitle,
                    board.boardContent,
                    board.versusSideA,
                    board.versusSideB,
                    board.userNo.userNickname.`as`("userNickname"),
                    Expressions.stringTemplate("gala.fn_code_nm({0},{1})",
                        Expressions.constant("category"),board.categoryCd).`as`("category"),
                    board.likeCount,
                    Expressions.stringTemplate("gala.fn_code_nm({0},{1})",
                        Expressions.constant("versusStatus"),board.versusStatus).`as`("versusStatus"),
                    board.crtDt,
                    board.endDate)
            )
            .from(board)
            .orderBy(orderSpecifier)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(conent,pageable,total)
    }

}