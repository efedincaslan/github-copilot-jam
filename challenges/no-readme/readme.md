# Music Album Catalog Manager

## Description

CompactDiscRestDataBoot is a Spring Boot REST API for managing a music album catalog. 

It provides complete CRUD (Create, Read, Update, Delete) functionality for compact disc records and associated tracks. 

The API is fully documented with integrated Swagger/OpenAPI UI, allowing developers to explore endpoints and test requests interactively. 

Built on Spring Data JPA for database operations and MySQL for data persistence.

## Prerequisites

- **Java 11** or higher
- **Apache Maven 3.6+**
- **MySQL 8.0+** (running locally or accessible remotely)
- **Port 8080** available (default application port)
- Git (for cloning the repository)

## Deployment

**Local Development:**
1. Clone or download the repository
2. Update database credentials in `src/main/resources/application.properties`:
   - `spring.datasource.url`: MySQL connection URL (default: `localhost:3306/conygre`)
   - `spring.datasource.username`: Database user (default: `root`)
   - `spring.datasource.password`: Database password
3. Initialize the database: `mysql -u root -p < sql/createTables.sql`
4. Build and run: `mvn clean spring-boot:run`
5. Access the API at `http://localhost:8080` and Swagger UI at `http://localhost:8080/swagger-ui.html`

**Docker Deployment:**
1. Build the application: `mvn clean package`
2. Use `src/main/resources/application-docker.properties` for container configuration
3. Deploy as a containerized Spring Boot application
4. Ensure MySQL container is running and accessible at `cddb:3306`

## Monitoring

The application includes built-in logging via Log4j2 with the following capabilities:

- **Log Output**: Console output and file logging to `myapplication.log`
- **Log Level**: INFO level by default for package `com.conygre.spring.boot`
- **Log Format**: Timestamps, log level, class name, line number, and message
- **API Monitoring**: Access Swagger UI at `/swagger-ui.html` to view available endpoints and test API calls
- **Request Logging**: All API operations (GET, POST, DELETE) are logged with operation details

For production deployments, consider adding Spring Boot Actuator for enhanced health checks and metrics collection.
