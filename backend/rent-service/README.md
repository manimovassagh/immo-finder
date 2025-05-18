# Rent Service

This service is part of the Immo-Finder microservices architecture, responsible for listing and retrieving rental properties.

## Service Details

- **Port**: 8082
- **Database**: PostgreSQL
- **Container Name**: rent-service-db
- **Network**: immo (shared network for all microservices)

## Getting Started

To run this service:

```bash
# Navigate to the rent-service directory
cd backend/rent-service

# Start the PostgreSQL database
docker compose up -d

# Run the Spring Boot application
./mvnw spring-boot:run
```

## API Endpoints

*To be implemented*

## Configuration

The service uses the following configuration:

- **Database**: PostgreSQL on port 5432
- **Database Name**: rent_service_db
- **Database User**: rental_user
- **Database Password**: rental_password

## Docker Network

All Immo-Finder microservices use the shared `immo` Docker network for inter-service communication.

## Port Allocation

The Immo-Finder microservices use the following port allocation:

| Service      | Port |
|--------------|------|
| Rent Service | 8082 |
| *Future services will use sequential ports* |

## Dependencies

- Spring Boot 3.4.5
- Spring Data JPA
- PostgreSQL Driver
- Spring Web 