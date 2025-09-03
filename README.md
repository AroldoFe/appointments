# Appointments - Medical Appointment Management System

This project implements a medical appointment management system, allowing the registration of patients, healthcare
professionals, medical procedures, and insurance plans, as well as scheduling appointments.

## Technologies Used

- **Kotlin**: Main programming language
- **Spring Boot**: Application development framework
- **Spring Data JPA**: For data persistence
- **PostgresSQL**: Relational database
- **Docker**: For containerization of the application and dependencies
- **JUnit 5**: For unit and integration testing
- **MockK**: Mocking framework for tests

## Database Structure

The system uses the following main entities:

- **User**: System users
- **Patient**: Patients
- **PatientPhone**: Patient phone numbers
- **Procedure**: Medical procedures
- **Insurance**: Medical insurance plans
- **Appointment**: Scheduled appointments

## Features

- User registration and management
- Patient registration and management
- Appointment scheduling
- Association of procedures with appointments
- Integration with medical insurance plans

## Validations

The system implements several custom validations:

- Username format validation
- Strong password validation
- Non-empty field validation after trimming
- Validation that at least one field is filled in partial updates

## How to Run

### Prerequisites

- Docker and Docker Compose
- JDK 21
- Gradle 8.14

### Execution Steps

1. Clone the repository:
   ```
   git clone https://github.com/AroldoFe/appointments.git
   cd appointments
   ```

2. Run PostgreSQL database via Docker:
   ```
   docker compose up -d
   ```

3. Run the application:
   ```
   ./gradlew bootRun -Dspring.profiles.active=dev
   ```

4. Access the application at:
   ```
   http://localhost:8080
   ```

## Tests

To run the tests:

```
./gradlew test -Dspring.profiles.active=test
```

## Project Structure

- `/src/main/kotlin/br/com/aroldofe/appointments`
    - `/controller`: REST controllers
    - `/domain`: Domain entities
    - `/dto`: Data transfer objects
    - `/repository`: Data access repositories
    - `/service`: Business services
    - `/validation`: Custom validators

- `/src/test/kotlin/br/com/aroldofe/appointments`
    - `/controller`: Controller tests
    - `/service`: Service tests
    - `/integration`: Integration tests
