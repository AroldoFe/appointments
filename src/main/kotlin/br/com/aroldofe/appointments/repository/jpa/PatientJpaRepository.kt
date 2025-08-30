package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.Patient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface PatientJpaRepository : JpaRepository<Patient, Long>, JpaSpecificationExecutor<Patient>

