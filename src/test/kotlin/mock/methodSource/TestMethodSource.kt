package mock.methodSource

import java.util.stream.Stream
import mock.RandomID
import org.junit.jupiter.params.provider.Arguments

object TestMethodSource {
    @JvmStatic
    fun invalidNames(): Stream<Arguments> = Stream.of(
        Arguments.of("    ", "Name must not be empty"),
        Arguments.of("Jo", "Name must be between 3 and 256 characters"),
        Arguments.of(RandomID.stringSize(257), "Name must be between 3 and 256 characters")
    )
}