package br.com.aroldofe.apointments.service.impl

import br.com.aroldofe.apointments.domain.Procedure
import br.com.aroldofe.apointments.dto.bo.ProcedureBO
import br.com.aroldofe.apointments.dto.converter.toBO
import br.com.aroldofe.apointments.dto.request.ProcedureRequest
import br.com.aroldofe.apointments.exception.definition.EntityAlreadyExistsException
import br.com.aroldofe.apointments.repository.ProcedureRepository
import br.com.aroldofe.apointments.repository.UserRepository
import br.com.aroldofe.apointments.service.AuthenticatedCreation
import org.springframework.stereotype.Service

@Service
class ProcedureServiceImpl(
    private val repository: ProcedureRepository,
    private val userRepository: UserRepository
): AuthenticatedCreation<ProcedureRequest, ProcedureBO> {
    override suspend fun create(data: ProcedureRequest, userPubId: String?): ProcedureBO {
        val user = this.userRepository.findByPubId(userPubId!!)!!
        val procedure = Procedure(name = data.name, creationUser = user)

        if (this.repository.findByPubId(procedure.pubId) != null) {
            throw EntityAlreadyExistsException(Procedure::class.java.name, procedure.pubId)
        }

        return this.repository.save(procedure).toBO()
    }
}