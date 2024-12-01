package com.galashow.gala.service


import com.galashow.gala.exception.NotFoundException
import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.model.entity.Board
import com.galashow.gala.model.entity.GalaUser
import com.galashow.gala.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(
    private val boardRepository: BoardRepository
){
    @Transactional
    fun createDummy(galaUser : GalaUser){
        for(i in 0..10){
            val board = Board(
                userNo = galaUser,
                boardTitle = "boardTitle$i",
                boardContent = "boardContent$i",
                categoryCd = "ETC",
                versusSideA = "sideA$i",
                versusSideB = "sideB$i"
            )
            boardRepository.save(board)
        }
    }

    @Transactional
    fun findAllBoard() : MutableList<BoardDTO>{
        return boardRepository.findAllBoardDTO()
    }

    @Transactional
    fun findBoardDTOByBoardNo(boardNo:Long) : BoardDTO{
        return boardRepository.findBoardDTOByBoardNo(boardNo) ?: throw NotFoundException()
    }
}