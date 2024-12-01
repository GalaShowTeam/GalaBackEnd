package com.galashow.gala.model.entity

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import org.hibernate.annotations.ColumnDefault
import java.io.Serializable
import java.time.Instant
import java.util.*

@Entity
@Table(name = "com_cd_i", schema = "gala")
class ComCdI(
    @EmbeddedId
    val id: ComCdIId? = null,

    @Column(name = "cd_nm", length = 20)
    var cdNm: String? = null,

    @ColumnDefault("now()")
    @Column(name = "crt_dt")
    var crtDt: Instant? = null,

    @ColumnDefault("now()")
    @Column(name = "chg_dt")
    var chgDt: Instant? = null,

    @ColumnDefault("'N'")
    @Column(name = "del_yn", length = 1)
    var delYn: String? = null
) {

}

@Embeddable
class ComCdIId : Serializable {
    @NotNull
    @Column(name = "cd_group", nullable = false, length = 15)
    var cdGroup: String? = null

    @NotNull
    @Column(name = "cd_value", nullable = false, length = 3)
    var cdValue: String? = null
    override fun hashCode(): Int = Objects.hash(cdGroup, cdValue)
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false

        other as ComCdIId

        return cdGroup == other.cdGroup &&
                cdValue == other.cdValue
    }

    companion object {
        private const val serialVersionUID = -6562136807064937179L
    }
}