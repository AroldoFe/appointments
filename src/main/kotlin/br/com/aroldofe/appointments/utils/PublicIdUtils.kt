package br.com.aroldofe.appointments.utils

import java.util.UUID

enum class EntityType(val prefixCode: String) {
    USER(PublicIdUtils.USER_PREFIX_CODE),
    PROCEDURE(PublicIdUtils.PROCEDURE_PREFIX_CODE),
}

object PublicIdUtils {
    const val USER_PREFIX_CODE = "USR"
    const val PROCEDURE_PREFIX_CODE = "PROC"

    fun generate(entityType: EntityType): String {
        return "${entityType.prefixCode}_${UUID.randomUUID().toString().uppercase()}"
    }
}