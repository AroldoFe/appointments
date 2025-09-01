package mock.dsl

import br.com.aroldofe.appointments.dto.request.UserRequest
import br.com.aroldofe.appointments.dto.request.UserUpdateRequest

@DslMarker
annotation class ApiTestDsl

fun createUserRequest(init: UserRequestBuilder.() -> Unit): UserRequest {
    val builder = UserRequestBuilder()
    builder.init()
    return builder.build()
}

fun updateUserRequest(init: UserUpdateRequestBuilder.() -> Unit): UserUpdateRequest {
    val builder = UserUpdateRequestBuilder()
    builder.init()
    return builder.build()
}

@ApiTestDsl
class UserRequestBuilder {
    private var name: String = "John Doe"
    private var email: String = "email@email.com"
    private var username = "jhon_doe"
    private var password = "StrongP@ssw0rd"

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

    fun build() = UserRequest(
        name = name,
        email = email,
        username = username,
        password = password
    )
}

@ApiTestDsl
class UserUpdateRequestBuilder {
    private var name: String? = "John Doe"
    private var email: String? = "email@email.com"
    private var username: String? = "jhon_doe"
    private var password: String? = "StrongP@ssw0rd"

    fun name(name: String?) {
        this.name = name
    }

    fun email(email: String?) {
        this.email = email
    }

    fun username(username: String?) {
        this.username = username
    }

    fun password(password: String?) {
        this.password = password
    }

    fun allNull() {
        this.name = null
        this.email = null
        this.username = null
        this.password = null
    }

    fun build() = UserUpdateRequest(
        name = name,
        email = email,
        username = username,
        password = password
    )
}