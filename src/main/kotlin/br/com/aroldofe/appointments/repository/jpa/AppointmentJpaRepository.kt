package br.com.aroldofe.appointments.repository.jpa

import br.com.aroldofe.appointments.domain.Appointment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface AppointmentJpaRepository : JpaRepository<Appointment, Long>, JpaSpecificationExecutor<Appointment>

