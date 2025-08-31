package mock.container

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@ContextConfiguration(initializers = [PostgresContainerInitializer.Initializer::class])
abstract class PostgresContainerInitializer {
    companion object {
        const val MEMORY_IN_BYTES = 2L * 1024L * 1024L * 1024L // 2GB

        fun createPostgresContainerWithInitScript(): PostgreSQLContainer<*> =
            PostgreSQLContainer(
                DockerImageName.parse("postgres:15")
            ).apply {
                withClasspathResourceMapping(
                    "db",
                    "/docker-entrypoint-initdb.d",
                    BindMode.READ_WRITE
                )
                withCreateContainerCmdModifier {
                    it.hostConfig?.withMemory(MEMORY_IN_BYTES)
                }
            }
    }

    internal class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
        override fun initialize(applicationContext: ConfigurableApplicationContext) {
            val postgresContainer = createPostgresContainerWithInitScript()
            postgresContainer.start()
            TestPropertyValues.of(
                "spring.datasource.url=${postgresContainer.jdbcUrl.replace("test", "appointments")}",
                "spring.datasource.username=appointment_usr",
                "spring.datasource.password=T9v@x2LpQw7zR1eS",
            ).applyTo(applicationContext)

            // Adiciona um shutdown hook para parar o container quando a JVM for encerrada
            Runtime.getRuntime().addShutdownHook(Thread {
                postgresContainer.stop()
            })
        }
    }
}