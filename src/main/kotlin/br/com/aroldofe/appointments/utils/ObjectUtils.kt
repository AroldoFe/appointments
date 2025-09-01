package br.com.aroldofe.appointments.utils

import br.com.aroldofe.appointments.config.JacksonConfig

fun Any.toJsonString(): String {
    val objectMapper = JacksonConfig().objectMapper()
    return objectMapper.writeValueAsString(this)
}