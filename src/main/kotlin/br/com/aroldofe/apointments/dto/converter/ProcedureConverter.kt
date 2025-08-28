package br.com.aroldofe.apointments.dto.converter

import br.com.aroldofe.apointments.domain.Procedure
import br.com.aroldofe.apointments.dto.bo.ProcedureBO

fun Procedure.toBO(): ProcedureBO {
    return ProcedureBO(this)
}