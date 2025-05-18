# Immo-Finder Backend

This directory contains the backend microservices for the Immo-Finder application.

## Microservices Architecture

The Immo-Finder backend is built using a microservices architecture, with each service responsible for a specific domain within the real estate application.

### Services

- **Rent Service** (Port: 8082) - For managing rental properties
- *Additional services will be added as needed*

### Shared Docker Network

All microservices use a shared Docker network called `immo` for inter-service communication.

```bash
# Create the shared network (if not already created)
docker network create immo
```

### Port Allocation

To avoid port conflicts, each microservice is assigned a specific port:

| Service      | Port |
|--------------|------|
| Rent Service | 8082 |
| *Future services* | 8083, 8084, etc. |

### Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.x
- **Database**: PostgreSQL (separate instance for each service)
- **Authentication**: Keycloak (centralized auth service)
- **Containerization**: Docker

## Getting Started

To run all services:

```bash
# First, create the shared Docker network (if not already created)
docker network create immo

# Then, start each service individually
cd rent-service
docker compose up -d
./mvnw spring-boot:run
```

## Development Guidelines

- Follow the principle of service isolation
- Each service should have its own database
- Use RESTful APIs for inter-service communication
- Document all APIs using OpenAPI/Swagger
- Use environment variables for configuration
- Follow database migration best practices
- Write unit and integration tests 