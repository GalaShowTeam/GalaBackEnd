package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Duration
import java.time.Instant
import java.time.Instant.now

@Entity
@Table(name = "board", schema = "gala")
class Board(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_no", nullable = false)
    val boardNo: Long? = null,

    @Size(max = 50)
    @NotNull
    @Column(name = "board_title", nullable = false, length = 50)
    var boardTitle: String,

    @Column(name = "board_content", length = Integer.MAX_VALUE)
    var boardContent: String? = null,

    @Size(max = 100)
    @NotNull
    @Column(name = "versus_side_a", nullable = false, length = 100)
    var versusSideA: String,

    @Size(max = 100)
    @NotNull
    @Column(name = "versus_side_b", nullable = false, length = 100)
    var versusSideB: String,

    @Size(max = 3)
    @NotNull
    @Column(name = "versus_status", nullable = false, length = 3)
    @ColumnDefault("'001'")
    var versusStatus: String? = "001",

    @ColumnDefault("0")
    @Column(name = "like_count")
    var likeCount: Int? = 0,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no", nullable = false)
    val userNo: GalaUser,

    @Size(max = 3)
    @NotNull
    @Column(name = "category_cd", nullable = false, length = 3)
    var categoryCd: String,

    @ColumnDefault("0")
    @Column(name = "report_count")
    var reportCount: Int? = 0,

    @Column(name = "end_date")
    var endDate: Instant? = now().plus(Duration.ofDays(10)),

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    val crtDt: Instant? = now(),

    @Column(name = "del_yn", length = 1)
    @ColumnDefault("'N'")
    var delYn: String? = "N"
) {

}