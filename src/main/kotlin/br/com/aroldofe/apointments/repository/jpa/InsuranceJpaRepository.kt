package br.com.aroldofe.apointments.repository.jpa

import br.com.aroldofe.apointments.domain.Insurance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface InsuranceJpaRepository : JpaRepository<Insurance, Long>, JpaSpecificationExecutor<Insurance>

