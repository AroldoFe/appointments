package br.com.aroldofe.appointments.domain

import br.com.aroldofe.appointments.enums.AppointmentStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "appointment")
class Appointment(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_seq")
    @SequenceGenerator(name = "appointment_seq", sequenceName = "appointment_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "pub_id", nullable = false, length = 50)
    val pubId: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Column(name = "datetime", nullable = false)
    val datetime: LocalDateTime,

    @Column(name = "duration", nullable = false)
    val duration: Int,

    @Column(name = "status", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    val status: AppointmentStatus = AppointmentStatus.SCHEDULED,

    @Column(name = "professional_name", nullable = false, length = 256)
    val professionalName: String,

    @Column(name = "creation_timestamp", nullable = false)
    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creation_user_id", nullable = false)
    val creationUser: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "insurance_id", nullable = false)
    val insurance: Insurance,

    @Column(name = "insurance_card_number", length = 100)
    val insuranceCardNumber: String? = null
)

