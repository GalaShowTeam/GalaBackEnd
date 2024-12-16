package com.galashow.gala.controller


import com.galashow.gala.common.dto.ResponseDTO
import com.galashow.gala.common.dto.ResponseDTOWithContents
import com.galashow.gala.model.dto.BoardDTO
import com.galashow.gala.security.MemberDetails
import com.galashow.gala.service.BoardService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/board")
class BoardController(
    private val boardService: BoardService
) {
    
    private val logger:Logger = LoggerFactory.getLogger(BoardController::class.java)

    @GetMapping("/createDummy")
    fun createDummy(authentication: Authentication):ResponseEntity<Any>{

        val loginUser = authentication.principal as? MemberDetails
            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        val galaUser = loginUser.getGalaUser()
        try{
            boardService.createDummy(galaUser)
            return ResponseEntity.status(HttpStatus.OK).body("생성 완료")
        } catch (e:Exception){
            logger.error(e.message)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("생성 실패")
        }
    }

    @Operation(
        summary = "게시글 정보 조회",
        description = "모든 게시글 정보를 조회합니다.",
        responses = [
            ApiResponse(
            responseCode = "200",
            description = "모든 게시글 정보 반환 성공",

            content = [
                Content(mediaType = "application/json",
                    schema = Schema(implementation = BoardDTO::class)
                )
            ]
        )
        ]
    )
    @GetMapping
    fun findAllBoard(
        @RequestParam(value = "page", defaultValue = "0")  page: Int,
        @RequestParam(value = "size", defaultValue = "10") size: Int,
        @RequestParam(value = "sort", defaultValue = "crtDt") sort: String,
        @RequestParam(value = "direction", defaultValue = "desc") direction: String,
        @RequestParam(value = "writer", required = false) writer: String?,
        @RequestParam(value = "title",required = false) title: String?,
        @RequestParam(value = "category", required = false) category: String?,
    ) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTOWithContents("SUCCESS","성공했습니다.",
            boardService.findAllBoard(page,size,sort,direction,writer,title,category)))
    }

    @Operation(
        summary = "게시글 정보 조회",
        description = "게시글 번호에 해당하는 게시글 정보를 반환합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 정보 반환 성공",
                content = [
                    Content(mediaType = "application/json",
                        schema = Schema(implementation = BoardDTO::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/{id}")
    fun findBoardByBoardNo(@PathVariable("id") boardNo:Long) : ResponseEntity<Any>{
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTOWithContents("SUCCESS","성공했습니다.",boardService.findBoardDTOByBoardNo(boardNo)))
    }

    @Operation(
        summary = "게시글 삭제",
        description = "게시글 번호에 해당하는 게시글을 삭제 합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 삭제 완료",
                content = [
                    Content(mediaType = "application/json",
                        schema = Schema(implementation = ResponseDTO::class)
                    )
                ]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteBoardByBoardNo(authentication: Authentication,@PathVariable("id") boardNo: Long) : ResponseEntity<Any>{
        val loginUser = authentication.principal as? MemberDetails
            ?:return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("적절하지 않은 사용자입니다.")

        val loginGalaUser = loginUser.getGalaUser()
        boardService.deleteBoardByBoardNo(loginGalaUser,boardNo)
        return ResponseEntity.status(HttpStatus.OK).body(ResponseDTO("SUCCESS","삭제 완료했습니다."))
    }
    
    @Operation(
        summary = "게시글 업데이트",
        description = "게시글 번호에 해당하는 게시글을 업데이트 한 후 업데이트된 게시글을 리턴합니다.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "게시글 업데이트 완료",
                content = [
                    Content(mediaType = "application/json",
                        schema = Schema(implementation = BoardDTO::class)
                    )
                ]
            )
        ]
    )
    @PutMapping("/{id}")
    fun updateBoard(authentication: Authentication,
                    @PathVariable("id") boardNo: Long,
                    ){
        //TODO: 작성 필요
    }
}