package br.com.aroldofe.apointments.repository.jpa

import br.com.aroldofe.apointments.domain.Procedure
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ProcedureJpaRepository : JpaRepository<Procedure, Long>, JpaSpecificationExecutor<Procedure>

