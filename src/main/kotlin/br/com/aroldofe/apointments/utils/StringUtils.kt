package br.com.aroldofe.apointments.utils

import java.security.MessageDigest

fun String.toSHA256(): String {
    val bytes = MessageDigest.getInstance("SHA-256")
        .digest(this.toByteArray(Charsets.UTF_8))
    return bytes.joinToString("") { "%02x".format(it) }
}