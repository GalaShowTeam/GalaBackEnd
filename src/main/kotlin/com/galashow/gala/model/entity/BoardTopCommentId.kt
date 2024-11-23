package com.galashow.gala.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.io.Serializable
import java.util.*

@Embeddable
class BoardTopCommentId : Serializable {
    @NotNull
    @Column(name = "board_no", nullable = false)
    var boardNo: Int? = null

    @NotNull
    @Column(name = "rank", nullable = false)
    var rank: Int? = null
    override fun hashCode(): Int = Objects.hash(boardNo, rank)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as BoardTopCommentId

        return boardNo == other.boardNo &&
                rank == other.rank
    }

    companion object {
        private const val serialVersionUID = 4697509062614722003L
    }
}