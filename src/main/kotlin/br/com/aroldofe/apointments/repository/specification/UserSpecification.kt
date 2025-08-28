package br.com.aroldofe.apointments.repository.specification

import br.com.aroldofe.apointments.domain.User
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import org.springframework.data.jpa.domain.Specification

class UserSpecification(
    val notId: Long? = null,
    val notPubId: String? = null,
    val id: Long? = null,
    val pubId: String? = null,
    val username: String? = null,
    val email: String? = null,
) : Specification<User> {
    companion object {
        fun findByUsername(username: String) = UserSpecification(username = username)
        fun findByEmail(email: String) = UserSpecification(email = email)
        fun findByUsernameOrEmail(username: String, email: String) = UserSpecification(username = username)
                .or(UserSpecification(email = email))
        fun findByPubId(pubId: String) = UserSpecification(pubId = pubId)

    }

    override fun toPredicate(
        root: Root<User?>,
        query: CriteriaQuery<*>?,
        criteriaBuilder: CriteriaBuilder
    ): Predicate? {
        val predicates = mutableListOf<Predicate>()

        notId?.let {
            predicates.add(criteriaBuilder.notEqual(root.get<Long>("id"), notId))
        }

        notPubId?.let {
            predicates.add(criteriaBuilder.notEqual(root.get<String>("pubId"), notPubId))
        }

        id?.let {
            predicates.add(criteriaBuilder.equal(root.get<Long>("id"), id))
        }

        pubId?.let {
            predicates.add(criteriaBuilder.equal(root.get<String>("pubId"), pubId))
        }

        username?.let {
            predicates.add(criteriaBuilder.equal(root.get<String>("username"), username.lowercase()))
        }

        email?.let {
            predicates.add(criteriaBuilder.equal(root.get<String>("email"), email.lowercase()))
        }

        return if (predicates.isEmpty()) {
            null
        } else {
            criteriaBuilder.and(*predicates.toTypedArray())
        }
    }
}