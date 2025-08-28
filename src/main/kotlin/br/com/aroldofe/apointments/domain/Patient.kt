package br.com.aroldofe.apointments.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime
import org.hibernate.annotations.Fetch
import org.hibernate.annotations.FetchMode

@Entity
@Table(name = "patient")
class Patient(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "patient_seq")
    @SequenceGenerator(name = "patient_seq", sequenceName = "patient_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "pub_id", nullable = false, length = 50, unique = true)
    val pubId: String,

    @Column(name = "name", nullable = false, length = 256)
    val name: String,

    @Column(name = "birth_date", nullable = false)
    val birthDate: LocalDate,

    @Column(name = "document", length = 50)
    val document: String? = null,

    @Column(name = "document_type", length = 20)
    val documentType: String? = null,

    @Column(name = "active", nullable = false)
    val active: Boolean = true,

    @Column(name = "creation_timestamp", nullable = false)
    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creation_user_id", nullable = false)
    val creationUser: User? = null,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "patient", cascade = [CascadeType.ALL], orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    val phones: Set<PatientPhone> = emptySet()
)
