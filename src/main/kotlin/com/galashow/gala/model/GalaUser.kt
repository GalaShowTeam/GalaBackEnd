package com.galashow.gala.model

import ch.qos.logback.core.status.InfoStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "gala_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class GalaUser (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val userNo: Long,

    @Column(length = 40)
    open val userEmail : String,

    open val provider : String,

    open var userNickname : String,

    open var profileImg : String = "",

    open val crtDt : Instant = Instant.now(),

    open var delYn : String = "N",

    open var lastLogin : Instant = Instant.now(),

    open val locked : String = "N",

    open var points : Long = 0,

    open val role : String = "002"


){
    override fun toString(): String {
        return "USER($userNo, $userEmail, $provider)"
    }
}

@Entity
class AdminUser (
    userNo : Long,
    userEmail : String,
    provider : String,
    userNickname : String,
) : GalaUser(
    userNo = userNo,
    userEmail = userEmail,
    provider = provider,
    userNickname = userNickname,
    role = "001"
){}

@Entity
class BaseUser (
    userNo : Long,
    userEmail : String,
    provider : String,
    userNickname : String,
) : GalaUser(
    userNo = userNo,
    userEmail = userEmail,
    provider = provider,
    userNickname = userNickname
    , role = "002"
){}