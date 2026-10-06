# Job Application Tracker API

REST API for tracking job applications: create, update, filter, and monitor your job search pipeline.

## Tech stack
Java 21, Spring Boot 4, Spring Data JPA (Hibernate), MySQL, Maven, Bean Validation, Swagger UI (springdoc), JUnit 5 and Mockito.

## Features
- Full CRUD for job applications
- Status pipeline: WISHLIST, APPLIED, INTERVIEW, OFFER, REJECTED, WITHDRAWN
- Filter by status and company name, with pagination and sorting
- Statistics endpoint (count per status)
- Input validation and consistent JSON error responses (400, 404)
- Layered architecture: controller, service, repository, DTOs, global exception handler

## Run locally
1. Install Java 21 and MySQL, then create the database: `CREATE DATABASE job_tracker;`
2. In the project root, create a file named `secrets.properties` (it is git-ignored) containing:
```
   spring.datasource.password=YOUR_MYSQL_PASSWORD
```
3. If your MySQL does not run on port 3307 or the user is not `root`, set the environment variables `DB_URL` and `DB_USERNAME`
   (for example `DB_URL=jdbc:mysql://localhost:3306/job_tracker?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`).
4. Start the app: `./mvnw spring-boot:run` (Windows PowerShell: `.\mvnw spring-boot:run`)
5. Open Swagger UI: http://localhost:8080/swagger-ui.html

## Endpoints
| Method | Path | Description |
|---|---|---|
| POST | /api/applications | Create an application |
| GET | /api/applications | List with `status`, `company`, `page`, `size`, `sortBy`, `direction` |
| GET | /api/applications/{id} | Get one |
| PUT | /api/applications/{id} | Update |
| PATCH | /api/applications/{id}/status | Change status |
| DELETE | /api/applications/{id} | Delete |
| GET | /api/applications/stats | Count per status |

## Tests
`./mvnw test` runs unit tests for the service layer (JUnit 5 and Mockito).

## Possible next steps
JWT authentication with per-user data, interview dates, Flyway migrations, Docker Compose, integration tests with Testcontainers.