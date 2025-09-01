package br.com.aroldofe.appointments.utils

object ValidPatterns {
    const val PUB_ID_REGEX =
        "[0-9A-F]{8}-[0-9A-F]{4}-[1-5][0-9A-F]{3}-[89abAB][0-9A-F]{3}-[0-9A-F]{12}"
    const val USER_PUB_ID_REGEX = "^USR_$PUB_ID_REGEX$"
    const val PROCEDURE_PUB_ID_REGEX = "^PRC_$PUB_ID_REGEX$"
    const val APPOINTMENT_PUB_ID_REGEX = "^APT_$PUB_ID_REGEX$"
}

object Constants {
    const val X_USER_ID = "x-user-id"
}