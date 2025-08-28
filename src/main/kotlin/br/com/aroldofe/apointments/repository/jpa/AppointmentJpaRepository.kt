package br.com.aroldofe.apointments.repository.jpa

import br.com.aroldofe.apointments.domain.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AppointmentJpaRepository : JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment>

