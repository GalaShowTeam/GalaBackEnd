package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant.now
import java.time.Instant

@Entity
@Table(name = "notification", schema = "gala")
class Notification(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_no", nullable = false)
    val notificationNo : Long? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no", nullable = false)
    val userNo: GalaUser,

    @Size(max = 255)
    @Column(name = "content")
    var content: String,

    @Size(max = 3)
    @Column(name = "type_cd", length = 3)
    var typeCd: String,

    @Column(name = "content_id")
    var contentId: Int,

    @Column(name = "is_read", length = 1)
    var isRead: String? = "N",

    @Column(name = "read_dt")
    var readDt: Instant? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    var crtDt: Instant? = now(),

    @ColumnDefault("'N'")
    @Column(name = "del_yn", length = 1)
    var delYn: String? = "N",
) {


}