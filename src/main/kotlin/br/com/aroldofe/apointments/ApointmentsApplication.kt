package br.com.aroldofe.apointments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApointmentsApplication

fun main(args: Array<String>) {
	runApplication<ApointmentsApplication>(*args)
}
