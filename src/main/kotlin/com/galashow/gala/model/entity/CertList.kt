package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.Instant
import java.time.Instant.now

@Entity
@Table(name = "cert_list", schema = "Gala")
class CertList(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cert_no", nullable = false)
    val certNo: Long? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no")
    val userNo: GalaUser? = null,

    @ColumnDefault("now()")
    @Column(name = "access_time" , columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val accessTime: Instant = now(),

    @Size(max = 45)
    @Column(name = "ip_address", length = 45)
    val ipAddress: String? = null,

    @Size(max = 3)
    @Column(name = "device_info_cd", length = 3)
    val deviceInfoCd: String? = null,

    @Size(max = 3)
    @Column(name = "access_status_cd", length = 3)
    val accessStatusCd: String? = null,

    @Size(max = 3)
    @Column(name = "fail_reason_cd", length = 3)
    val failReasonCd: String? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    val crtDt: Instant = now(),

    @Size(max = 1)
    @Column(name = "del_yn", length = 1)
    var delYn: String = "N"
){
}