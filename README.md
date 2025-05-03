
# 🏠 Immobilien Microservices Platform

A scalable, modular real estate platform built using microservices architecture, designed to handle both **rental** and **sales** listings, complete with search, photos, favorites, and secure access via Keycloak.

---

## 📐 Architecture Overview

The system is built around the **Separation of Property Type and Purpose** (e.g., Apartment for Rent vs House for Sale), with microservices managing dedicated domains and communicating over REST (optionally with async messaging later).

Each service is **independently deployable**, **JWT-secured**, and backed by a dedicated database.

---

## 🧩 Microservices & Names

| Microservice        | Description                                   | Repo/Service Name      |
|---------------------|-----------------------------------------------|------------------------|
| **Rent Service**     | Manages all listings available for rent       | `rent-service`         |
| **Buy Service**      | Manages all listings available for sale       | `buy-service`          |
| **Photo Service**    | Handles image uploads, metadata, and serving  | `photo-service`        |
| **Favorite Service** | Tracks user ↔ listing relationships           | `favorite-service`     |
| **Search Service**   | Aggregates data for full-text and geo search  | `search-service`       |
| **Auth Gateway**     | API gateway with JWT validation & routing     | `auth-gateway`         |
| **Keycloak**         | Identity Provider (external container)        | `keycloak`             |
| **Audit Service** *(optional)* | Logs user actions                  | `audit-service`        |
| **Analytics Service** *(optional)* | Tracks usage metrics           | `analytics-service`    |

---

## 🗃️ Databases

| Database      | Used By                        |
|---------------|--------------------------------|
| PostgreSQL     | rent-service, buy-service, photo-service, favorite-service, keycloak |
| Elasticsearch  | search-service                |
| Redis *(opt)*  | favorite-service cache        |
| File System/S3 | photo-service (for image files) |
| TimescaleDB *(opt)* | analytics-service        |
| MongoDB *(opt)* | audit-service (or PostgreSQL) |

---

## 🔐 Authentication & Authorization

- Managed by **Keycloak** (username/password, social login, roles)
- All services are protected via **JWT**
- API Gateway (`auth-gateway`) verifies tokens and enforces routing rules
- Services extract `userId` from token (`sub` claim)

---

## 🧠 Domain Design Highlights

- **Separate databases and services** for `rent` and `buy` domains
- **Different property types (apartment, house, land)** stored in separate tables
- **Photos** and **favorites** are shared across both Rent & Buy domains
- **Search Service** indexes listings from both rent & buy via background sync or webhook

---

## 🛠 Technologies Used

- **Spring Boot 3+** (Java 21)
- **Spring Cloud Gateway** (API Gateway)
- **PostgreSQL** for relational storage
- **Elasticsearch** for search
- **Redis** for caching favorites
- **Docker Compose** for local dev orchestration
- **Keycloak** for IAM
- **React + TypeScript** frontend *(not included here)*

---

## 🚀 API Example

### ✅ Create a Rent Listing

```http
POST /api/rent/apartments
Authorization: Bearer <token>
Content-Type: application/json

{
  "title": "Sunny Loft in Munich",
  "price": 1200,
  "rooms": 2,
  "size": 75,
  "address": {
    "street": "Hauptstraße",
    "houseNumber": "23A",
    "flatNumber": "4B",
    "floor": "3",
    "postalCode": "80331",
    "city": "München"
  }
}
```

### 📸 Upload Photos

```http
POST /api/photos/{listingId}
Content-Type: multipart/form-data
Authorization: Bearer <token>

files[]: photo1.jpg
files[]: photo2.jpg
```

### ❤️ Mark Favorite

```http
POST /api/favorites
Authorization: Bearer <token>

{
  "listingId": "uuid",
  "listingType": "RENT"
}
```

---

## 📦 Running the Stack (Dev Mode)

```bash
# Start all services
docker-compose up --build
```

Make sure to configure:
- Keycloak realm and clients
- Database connection strings via `application.yml` per service

---

## 🧪 Tests

Each service contains:
- Unit tests with JUnit 5
- Integration tests (with Testcontainers or MockMvc)
- REST API docs via Swagger/OpenAPI

---

## 🎯 Future Enhancements

- Message-based event sync (Kafka)
- Subscription service for agents (Stripe)
- Chat/messaging between buyer & seller
- Advanced recommendation engine

---

## 👤 Maintained By

**Mani Movassagh**  
Senior Software & Test Automation Engineer  
🇩🇪 Mainz, Germany
