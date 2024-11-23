package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant
import java.time.OffsetDateTime

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

    @ColumnDefault("N")
    @Size(max = 1)
    @Column(name = "del_yn", length = 1)
    var delYn: String? = null,
) {

}