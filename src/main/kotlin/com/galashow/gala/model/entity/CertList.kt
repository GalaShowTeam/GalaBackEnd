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
    open var id: Long? = null,

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_no")
    open var userNo: GalaUser? = null,

    @ColumnDefault("now()")
    @Column(name = "access_time")
    open var accessTime: Instant = now(),

    @Size(max = 45)
    @Column(name = "ip_address", length = 45)
    open var ipAddress: String? = null,

    @Size(max = 3)
    @Column(name = "device_info_cd", length = 3)
    open var deviceInfoCd: String? = null,

    @Size(max = 3)
    @Column(name = "access_status_cd", length = 3)
    open var accessStatusCd: String? = null,

    @Size(max = 3)
    @Column(name = "fail_reason_cd", length = 3)
    open var failReasonCd: String? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    open var crtDt: Instant = now(),

    @Column(name = "del_yn", length = Integer.MAX_VALUE)
    open var delYn: String = "N"
){
}