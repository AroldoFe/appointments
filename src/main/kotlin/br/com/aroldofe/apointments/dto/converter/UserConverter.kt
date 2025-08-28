package br.com.aroldofe.apointments.dto.converter

import br.com.aroldofe.apointments.domain.User
import br.com.aroldofe.apointments.dto.bo.UserBO

fun User.toBO(): UserBO {
    return UserBO(this)
}