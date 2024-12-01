package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant

@Entity
@Table(name = "comment", schema = "gala")
class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    val commentNo: Int? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "topic_no", nullable = false)
    val topicNo: Topic? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no", nullable = false)
    val userNo: GalaUser? = null,

    @Size(max = 500)
    @Column(name = "comment_content", length = 500)
    var commentContent: String? = null,

    @Column(name = "parent_comment_no")
    val parentCommentNo: Int? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    val crtDt: Instant? = null,

    @Column(name = "upt_dt")
    var uptDt: Instant? = null,

    @Size(max = 1)
    @Column(name = "del_yn", length = 1)
    @ColumnDefault("'N'")
    var delYn: String? = null,

    @Size(max = 3)
    @Column(name = "comment_side", length = 3)
    val commentSide: String? = null
) {

}