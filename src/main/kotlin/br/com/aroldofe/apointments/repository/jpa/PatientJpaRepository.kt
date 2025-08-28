package br.com.aroldofe.apointments.repository.jpa

import br.com.aroldofe.apointments.domain.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PatientJpaRepository : JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient>

