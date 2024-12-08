package com.galashow.gala.repository

import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.model.entity.Board
import com.galashow.gala.model.entity.QBoard
import com.galashow.gala.model.entity.QComCdI
import com.querydsl.core.BooleanBuilder
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
    fun findAllBoardDTO(pageable:Pageable,sort: String,
                        direction : String?,
                        writer : String?,
                        title :String?,
                        category:String?) : Page<BoardDTO>
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

    override fun findAllBoardDTO(pageable: Pageable,
                                 sort:String,
                                 direction : String?,
                                 writer : String?,
                                 title :String?,
                                 category:String?
                                 ): Page<BoardDTO> {

        val isDescending = direction?.equals("desc",ignoreCase = true) ?: false
        val orderSpecifier : OrderSpecifier<*> = when(sort){
            "likeCount" -> if(isDescending) board.likeCount.desc() else board.likeCount.asc()
            else -> if(isDescending) board.crtDt.desc() else board.crtDt.asc()
        }

        val builder = BooleanBuilder().apply {
            if (!writer.isNullOrBlank()) {
                and(board.userNo.userNickname.startsWithIgnoreCase(writer))
            }
            if (!title.isNullOrEmpty()) {
                and(board.boardTitle.startsWithIgnoreCase(title))
            }
            if (!category.isNullOrEmpty()) {
                and(board.categoryCd.eq(queryFactory
                    .select(categoryCdI.id.cdValue)
                    .from(categoryCdI)
                    .where(
                        categoryCdI.cdNm.eq(category)
                            .and(categoryCdI.id.cdGroup.eq("category"))
                    )
                    .fetchOne()?:""))

            }
        }

        val total = queryFactory
            .select(board.count())
            .from(board)
            .where(builder)
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
            .where(builder)
            .orderBy(orderSpecifier)
            .limit(pageable.pageSize.toLong())
            .offset(pageable.offset)
            .fetch()

        return PageImpl(conent,pageable,total)
    }

}