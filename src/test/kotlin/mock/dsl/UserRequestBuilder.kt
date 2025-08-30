package mock.dsl

import br.com.aroldofe.apointments.dto.request.UserRequest

@DslMarker
annotation class ApiTestDsl

fun createUserRequest(init: UserRequestBuilder.() -> Unit): UserRequest {
    val builder = UserRequestBuilder()
    builder.init()
    return builder.build()
}

@ApiTestDsl
class UserRequestBuilder {
    var name: String = "John Doe"
    var email: String = "email@email.com"
    var username = "jhon_doe"
    var password = "strongpassword"

    fun name(name: String) {
        this.name = name
    }

    fun email(email: String) {
        this.email = email
    }

    fun username(username: String) {
        this.username = username
    }

    fun password(password: String) {
        this.password = password
    }

    fun build() = br.com.aroldofe.apointments.dto.request.UserRequest(
        name = name,
        email = email,
        username = username,
        password = password
    )
}