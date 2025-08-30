package br.com.aroldofe.appointments.repository.impl

import br.com.aroldofe.appointments.domain.Procedure
import br.com.aroldofe.appointments.repository.ProcedureRepository
import br.com.aroldofe.appointments.repository.jpa.ProcedureJpaRepository
import br.com.aroldofe.appointments.repository.specification.ProcedureSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Repository

@Repository
class ProcedureRepositoryImpl(
    private val repository: ProcedureJpaRepository
) : ProcedureRepository {
    override suspend fun save(procedure: Procedure): Procedure = this.repository.save(procedure)

    override suspend fun findById(id: Long): Procedure? {
        return this.repository.findById(id).orElse(null)
    }

    override suspend fun findByPubId(pubId: String): Procedure? {
        return this.findOne(ProcedureSpecification.findByPubId(pubId))
    }

//    override suspend fun findAll(specification: Specification<Procedure>): List<Procedure> {
//        TODO("Not yet implemented")
//    }

    override suspend fun findAll(specification: Specification<Procedure>, pageable: Pageable): Page<Procedure> {
        TODO("Not yet implemented")
    }

    override suspend fun findOne(specification: Specification<Procedure>): Procedure? {
        return this.repository.findOne(specification)
            .orElse(null)
    }
}