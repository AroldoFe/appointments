package br.com.aroldofe.apointments.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "user")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    val id: Long? = null,

    @Column(name = "pub_id", nullable = false, length = 50, unique = true)
    // TODO criar UUID7 automaticamente
    val pubId: String,

    @Column(name = "name", nullable = false, length = 256)
    val name: String,

    @Column(name = "email", nullable = false, length = 256, unique = true)
    val email: String,

    @Column(name = "username", nullable = false, length = 100, unique = true)
    val username: String,

    @Column(name = "password", nullable = false, length = 256)
    val password: String,

    @Column(name = "creation_timestamp", nullable = false)
    val creationTimestamp: LocalDateTime = LocalDateTime.now(),

    @Column(name = "active", nullable = false)
    val active: Boolean = true
)

