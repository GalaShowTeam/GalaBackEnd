package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "board_top_topics", schema = "gala")
class BoardTopTopic {
    @EmbeddedId
    var id: BoardTopTopicId? = null

    @MapsId("boardNo")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "board_no", nullable = false)
    var boardNo: Board? = null

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "topic_no")
    var topicNo: Topic? = null

    @Size(max = 3)
    @Column(name = "rank_type_cd", length = 3)
    var rankTypeCd: String? = null

    @Column(name = "crt_dt")
    var crtDt: Instant? = null

    @ColumnDefault("'N'")
    @Size(max = 1)
    @Column(name = "del_yn", length = 1)
    var delYn: String? = null
}