package br.com.aroldofe.apointments.repository.specification

import br.com.aroldofe.apointments.domain.Procedure
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class ProcedureSpecification(
    val id: Long? = null,
    val pubId: String? = null,
    val active: Boolean? = null,
) : Specification<Procedure> {
    companion object {
        fun findById(id: Long) = ProcedureSpecification(id = id)
        fun findByPubId(pubId: String) = ProcedureSpecification(pubId = pubId)
    }

    override fun toPredicate(
        root: Root<Procedure?>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        id?.let {
            predicates.add(criteriaBuilder.equal(root.get<Long>("id"), id))
        }

        pubId?.let {
            predicates.add(criteriaBuilder.equal(root.get<String>("pubId"), pubId))
        }

        active?.let {
            predicates.add(criteriaBuilder.equal(root.get<Boolean>("active"), active))
        }

        return if (predicates.isEmpty()) {
            null
        } else {
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}