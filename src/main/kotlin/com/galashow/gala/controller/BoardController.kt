package com.galashow.gala.controller

import com.galashow.gala.common.dto.ResponseDTO
import com.galashow.gala.common.dto.ResponseDTOWithContents
import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.service.BoardService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {
    //TODO : API 문서 작성하기
    private val logger:Logger = LoggerFactory.getLogger(BoardController::class.java)

    @GetMapping("/createDummy")
    fun createDummy(authentication: Authentication):ResponseEntity<Any>{

        val loginUser = authentication.principal as? MemberDetails
            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        val galaUser = loginUser.getGalaUser()
        try{
            boardService.createDummy(galaUser)
            return ResponseEntity.status(HttpStatus.OK).body("생성 완료")
        }catch (e:Exception){
            logger.error(e.message)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("생성 실패")
        }
    }

    @GetMapping
    fun findAllBoard() : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTOWithContents("SUCCESS","성공했습니다.",boardService.findAllBoard()))
    }

    @GetMapping("/{id}")
    fun findBoardByBoardNo(@PathVariable("id") boardNo:Long) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTOWithContents("SUCCESS","성공했습니다.",boardService.findBoardDTOByBoardNo(boardNo)))
    }
}