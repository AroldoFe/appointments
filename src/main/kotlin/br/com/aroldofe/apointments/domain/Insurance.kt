package br.com.aroldofe.apointments.domain

import jakarta.persistence.*

@Entity
@Table(name = "insurance")
data class Insurance(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "insurance_seq")
    @SequenceGenerator(name = "insurance_seq", sequenceName = "insurance_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "pub_id", nullable = false, length = 50, unique = true)
    val pubId: String,

    @Column(name = "name", nullable = false, length = 256)
    val name: String,

    @Column(name = "active", nullable = false)
    val active: Boolean = true
)

