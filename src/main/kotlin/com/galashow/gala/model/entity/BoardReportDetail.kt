package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "board_report_details", schema = "gala")
class BoardReportDetail(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no", nullable = false)
    var reportNo : Int? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_no", nullable = false)
    var boardNo: Board,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "reporter_no", nullable = false)
    var reporterNo: GalaUser,

    @Size(max = 200)
    @NotNull
    @Column(name = "report_content", nullable = false, length = 200)
    var reportContent: String,

    @Size(max = 3)
    @NotNull
    @Column(name = "report_group_cd", nullable = false, length = 3)
    var reportGroupCd: String,

    @NotNull
    @Column(name = "crt_dt", nullable = false)
    @ColumnDefault("now()")
    var crtDt: Instant? = null,

    @NotNull
    @Size(max = 1)
    @ColumnDefault("'N'")
    @Column(name = "del_yn", nullable = false, length = 1)
    var delYn: String? = null
) {

}