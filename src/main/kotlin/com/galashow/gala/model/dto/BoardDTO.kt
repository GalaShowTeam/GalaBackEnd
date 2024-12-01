package com.galashow.gala.model.dto

import com.galashow.gala.common.dto.ContentResponse
import com.galashow.gala.model.entity.Board
import io.swagger.v3.oas.annotations.media.Schema
import java.time.Instant

@Schema(name = "BoardDTO", description = "BoardDTO 응답 객체")
data class BoardDTO(
    var boardNo : Long?,

    var boardTitle : String,

    var boardContent : String,

    var versusSideA : String,

    var versusSideB : String,

    var userNickname : String,

    var category : String,

    var likeCount : Int?,

    var versusStatus : String?,

    var crtDt : Instant?,

    var endDate : Instant?,



    ) : ContentResponse(){
        companion object{
            fun toDto(entity : Board,category: String) : BoardDTO{
                val userNickname = entity.userNo.userNickname.ifEmpty {
                    "익명${entity.userNo.userNo}"
                }

                return BoardDTO(
                    boardNo = entity.boardNo,
                    boardTitle = entity.boardTitle,
                    boardContent = entity.boardContent!!,
                    versusSideA = entity.versusSideA,
                    versusSideB = entity.versusSideB,
                    userNickname = userNickname,
                    category = category,
                    crtDt = entity.crtDt,
                    likeCount = entity.likeCount,
                    versusStatus = entity.versusStatus,
                    endDate = entity.endDate
                )

            }
        }
}