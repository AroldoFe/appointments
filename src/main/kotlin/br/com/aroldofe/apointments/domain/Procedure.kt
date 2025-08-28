package br.com.aroldofe.apointments.domain

import br.com.aroldofe.apointments.utils.EntityType
import br.com.aroldofe.apointments.utils.PublicIdUtils
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
import java.time.LocalDateTime

@Entity
@Table(name = "procedure")
class Procedure(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "procedure_seq")
    @SequenceGenerator(name = "procedure_seq", sequenceName = "procedure_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "pub_id", nullable = false, length = 50, unique = true)
    val pubId: String = PublicIdUtils.generate(EntityType.PROCEDURE),

    @Column(name = "name", nullable = false, length = 256)
    var name: String,

    @Column(name = "active", nullable = false)
    var active: Boolean = true,

    @Column(name = "creation_timestamp", nullable = false)
    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creation_user_id", nullable = false)
    var creationUser: User? = null
)

