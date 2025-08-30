package br.com.aroldofe.appointments.dto.converter

import br.com.aroldofe.appointments.domain.User
import br.com.aroldofe.appointments.dto.bo.UserBO

fun User.toBO(): UserBO {
    return UserBO(this)
}