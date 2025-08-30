package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.Insurance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface InsuranceJpaRepository : JpaRepository<Insurance, Long>, JpaSpecificationExecutor<Insurance>

