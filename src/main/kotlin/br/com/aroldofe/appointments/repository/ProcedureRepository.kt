package br.com.aroldofe.appointments.repository

import br.com.aroldofe.appointments.domain.Procedure
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification

interface ProcedureRepository {
    suspend fun save(procedure: Procedure): Procedure

    suspend fun findById(id: Long): Procedure?
    suspend fun findByPubId(pubId: String): Procedure?

//    suspend fun findAll(specification: Specification<Procedure>): List<Procedure>
    suspend fun findAll(specification: Specification<Procedure>, pageable: Pageable): Page<Procedure>

    suspend fun findOne(specification: Specification<Procedure>): Procedure?
}