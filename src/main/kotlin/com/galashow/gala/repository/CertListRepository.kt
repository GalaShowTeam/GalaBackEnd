package com.galashow.gala.repository

import com.galashow.gala.model.entity.CertList
import org.springframework.data.jpa.repository.JpaRepository

interface CertListRepository : JpaRepository<CertList, Long> {
}