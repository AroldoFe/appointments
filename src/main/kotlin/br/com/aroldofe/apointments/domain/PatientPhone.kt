package br.com.aroldofe.apointments.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "patient_phone")
class PatientPhone(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_phone_seq")
    @SequenceGenerator(name = "patient_phone_seq", sequenceName = "patient_phone_seq", allocationSize = 1)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    val patient: Patient,

    @Column(name = "phone_number", nullable = false, length = 50)
    val phoneNumber: String
)
