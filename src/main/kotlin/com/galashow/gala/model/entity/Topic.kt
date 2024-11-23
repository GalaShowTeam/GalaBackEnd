package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "topic", schema = "gala")
class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "topic_no", nullable = false)
    val topicNo: Int? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_no", nullable = false)
    val boardNo: Board,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no", nullable = false)
    val userNo: GalaUser,

    @NotNull
    @Column(name = "order_by", nullable = false)
    var orderBy: Int? = null,

    @Size(max = 100)
    @NotNull
    @Column(name = "topic_title", nullable = false, length = 100)
    val topicTitle: String,

    @Size(max = 3)
    @NotNull
    @Column(name = "stat_cd", nullable = false, length = 3)
    var statCd: String,

    @ColumnDefault("0")
    @Column(name = "like_count")
    var likeCount: Int? = null,

    @ColumnDefault("0")
    @Column(name = "report_count")
    var reportCount: Int? = null,

    @Column(name = "end_date")
    var endDate: Instant,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    val crtDt: Instant? = null,

    @Size(max = 1)
    @ColumnDefault("N")
    @Column(name = "del_yn", length = 1)
    var delYn: String? = null
) {

}