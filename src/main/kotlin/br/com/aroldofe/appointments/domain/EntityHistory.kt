package br.com.aroldofe.appointments.domain

import br.com.aroldofe.appointments.enums.EntityName
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType.STRING
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table
class EntityHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "entity_history_seq")
    @SequenceGenerator(name = "entity_history_seq", sequenceName = "entity_history_seq", allocationSize = 1)
    val id: Long? = null,

    @Column
    val entityId: Long,

    @Column(name = "date_created", nullable = false, length = 100)
    @Enumerated(STRING)
    val entityName: EntityName,

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "user_id", nullable = false)
    val createdBy: User,

    @Column(name = "creation_timestamp", nullable = false)
    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false)
    val history: String,
)