package com.galashow.gala.model

import ch.qos.logback.core.status.InfoStatus
import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "gala_user", schema = "gala")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
abstract class GalaUser (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val userNo: Long = 0,

    @Column(length = 40)
    open val userEmail : String,

    open val provider : String,

    open var userNickname : String = "",

    open var profileImg : String = "",

    open val crtDt : Instant = Instant.now(),

    open var delYn : String = "N",

    open var lastLogin : Instant = Instant.now(),

    open val locked : String = "N",

    open var points : Long = 0,

    @Column(insertable = false, updatable = false)
    open val role : String = "002",

    open val providerId : String


){
    override fun toString(): String {
        return "USER($userNo, $userEmail, $provider)"
    }
}

@Entity
@DiscriminatorValue("001")
class AdminUser (
    userEmail : String,
    provider : String,
    providerId : String,
) : GalaUser(
    userEmail = userEmail,
    provider = provider,
    role = "001",
    providerId = providerId
){}

@Entity

@DiscriminatorValue("002")
class BaseUser (
    userEmail : String,
    provider : String,
    providerId : String
) : GalaUser(
    userEmail = userEmail,
    provider = provider,
     role = "002"
    ,providerId = providerId
){}