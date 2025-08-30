package br.com.aroldofe.appointments

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ApointmentsApplication

fun main(args: Array<String>) {
	runApplication<ApointmentsApplication>(*args)
}
