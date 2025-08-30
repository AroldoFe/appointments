package br.com.aroldofe.appointments.dto.converter

import br.com.aroldofe.appointments.domain.Procedure
import br.com.aroldofe.appointments.dto.bo.ProcedureBO

fun Procedure.toBO(): ProcedureBO {
    return ProcedureBO(this)
}