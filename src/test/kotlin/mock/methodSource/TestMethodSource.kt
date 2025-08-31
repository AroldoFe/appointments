package mock.methodSource

import br.com.aroldofe.appointments.exception.definition.ErrorMessage
import java.util.stream.Stream
import org.junit.jupiter.params.provider.Arguments

object TestMethodSource {
    @JvmStatic
    fun invalidNames(): Stream<Arguments> {
        val expectedErrorMessageSize = ErrorMessage(
            error = "invalid_field",
            parameterName = "name",
            description = "Name must be between 3 and 256 characters"
        )

        val expectedErrorBlank = ErrorMessage(
            error = "invalid_field",
            parameterName = "name",
            description = "Name must not be empty"
        )

        return Stream.of(
            Arguments.of("", listOf(expectedErrorBlank, expectedErrorMessageSize)),
            Arguments.of("  ", listOf(expectedErrorBlank, expectedErrorMessageSize)),
            Arguments.of("    ", listOf(expectedErrorBlank)),
            Arguments.of("Jo", listOf(expectedErrorMessageSize)),
            Arguments.of("A".repeat(257), listOf(expectedErrorMessageSize)),
        )
    }

    @JvmStatic
    fun invalidEmails(): Stream<Arguments> {
        val expectedErrorMessageSize = ErrorMessage(
            error = "invalid_field",
            parameterName = "email",
            description = "E-mail must be between 3 and 256 characters"
        )

        val expectedErrorBlank = ErrorMessage(
            error = "invalid_field",
            parameterName = "email",
            description = "Email must not be empty"
        )

        val expectedErrorFormat = ErrorMessage(
            error = "invalid_field",
            parameterName = "email",
            description = "Email must be valid"
        )

        return Stream.of(
            Arguments.of("", listOf(expectedErrorBlank, expectedErrorMessageSize)),
            Arguments.of("  ", listOf(expectedErrorBlank, expectedErrorMessageSize, expectedErrorFormat)),
            Arguments.of("    ", listOf(expectedErrorBlank, expectedErrorFormat)),
            Arguments.of("a".repeat(250) + "@email.com", listOf(expectedErrorMessageSize, expectedErrorFormat)),
            Arguments.of("invalid-email", listOf(expectedErrorFormat)),
            Arguments.of("invalid@.com", listOf(expectedErrorFormat)),
            Arguments.of("invalid@com.", listOf(expectedErrorFormat)),
        )
    }

    @JvmStatic
    fun invalidUsernames(): Stream<Arguments> {
        val expectedErrorMessageSize = ErrorMessage(
            error = "invalid_field",
            parameterName = "username",
            description = "Username must be between 3 and 100 characters"
        )
        val expectedErrorBlank = ErrorMessage(
            error = "invalid_field",
            parameterName = "username",
            description = "Username must not be empty"
        )
        val expectedErrorFormat = ErrorMessage(
            error = "invalid_field",
            parameterName = "username",
            description = "Username must contain only letters, numbers, underscore, and dots. It cannot start with a number or dot."
        )

        return Stream.of(
            Arguments.of("", listOf(expectedErrorBlank, expectedErrorMessageSize, expectedErrorFormat)),
            Arguments.of("  ", listOf(expectedErrorBlank, expectedErrorMessageSize, expectedErrorFormat)),
            Arguments.of("    ", listOf(expectedErrorBlank, expectedErrorFormat)),
            Arguments.of("Jo", listOf(expectedErrorMessageSize)),
            Arguments.of("A".repeat(101), listOf(expectedErrorMessageSize)),
            Arguments.of("invalid username", listOf(expectedErrorFormat)),
            Arguments.of("invalid-username!", listOf(expectedErrorFormat)),
            Arguments.of("a space", listOf(expectedErrorFormat)),
            Arguments.of(".startsWithDot", listOf(expectedErrorFormat)),
            Arguments.of("123startsWithNumber", listOf(expectedErrorFormat)),
            Arguments.of("user@domain", listOf(expectedErrorFormat)),
            Arguments.of("user#hash", listOf(expectedErrorFormat)),
            Arguments.of("user-hyphen", listOf(expectedErrorFormat)),
            Arguments.of("special\$char", listOf(expectedErrorFormat)),
            Arguments.of("with spaces", listOf(expectedErrorFormat)),
        )
    }
}