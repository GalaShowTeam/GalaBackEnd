package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Size
import org.hibernate.annotations.ColumnDefault
import java.time.Instant

@Entity
@Table(name = "gala_user", schema = "gala")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
abstract class GalaUser (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    open val userNo: Long = 0,

    @Column(name = "user_email", length = 40, nullable = false)
    open val userEmail : String,

    @Size(max = 3)
    @Column(name = "provider",length = 3,nullable = false)
    open val provider : String,

    @Size(max = 10, message = "닉네임은 10자 이상 넘을 수 없습니다.")
    @Column(name= "user_nickname",length = 10)
    open var userNickname : String = "",

    @Column(name = "profile_img")
    open var profileImg : String = "",

    @ColumnDefault("now()")
    @Column(name ="crt_dt",nullable = false , columnDefinition = "TIMESTAMP WITH TIME ZONE")
    open val crtDt : Instant = Instant.now(),

    @Size(max = 1)
    @Column(name = "del_yn", nullable = false,length = 1)
    open var delYn : String = "N",

    @ColumnDefault("now()")
    @Column(name = "last_login",  columnDefinition = "TIMESTAMP WITH TIME ZONE")
    open var lastLogin : Instant = Instant.now(),

    @Size(max = 1)
    @Column(name = "locked",length = 1)
    open val locked : String = "N",

    @Column(name = "points")
    open var points : Long = 0,

    @Size(max = 3)
    @Column(name = "role",insertable = false, updatable = false, length = 3)
    open val role : String = "USR",

    @Column(name = "provider_id", nullable = false,unique = true)
    open val providerId : String


){
    override fun toString(): String {
        return "USER($userNo, $userEmail, $provider)"
    }
}

@Entity
@DiscriminatorValue("ADM")
class AdminUser (
    userEmail : String,
    provider : String,
    providerId : String,
) : GalaUser(
    userEmail = userEmail,
    provider = provider,
    role = "ADM",
    providerId = providerId
){}

@Entity

@DiscriminatorValue("USR")
class BaseUser (
    userEmail : String,
    provider : String,
    providerId : String
) : GalaUser(
    userEmail = userEmail,
    provider = provider,
     role = "USR"
    ,providerId = providerId
){}