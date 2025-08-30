package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.Procedure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProcedureJpaRepository : JpaRepository<Procedure, Long>, JpaSpecificationExecutor<Procedure>

