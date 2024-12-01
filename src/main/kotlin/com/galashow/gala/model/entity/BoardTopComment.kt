package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.Hibernate
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.io.Serializable
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "board_top_comments", schema = "gala")
class BoardTopComment(
    @EmbeddedId
    var id: BoardTopCommentId? = null,

    @MapsId("boardNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_no", nullable = false)
    var boardNo: Board? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "comment_no")
    var commentNo: Comment? = null,

    @Size(max = 3)
    @Column(name = "rank_type_cd", length = 3)
    var rankTypeCd: String? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    var crtDt: Instant? = null,

    @ColumnDefault("'N'")
    @Size(max = 1)
    @Column(name = "del_yn", length = 1)
    var delYn: String? = null,
) {

}

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