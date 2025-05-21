# Testing the Rent Service API with curl

This document provides curl commands to test the Rent Service API endpoints.

## Create Apartment

The following curl command creates a new apartment:

```bash
curl -X POST http://localhost:8080/api/apartments \
  -H "Content-Type: application/json" \
  -d '{
  "title": "Modern 2-bedroom apartment in Berlin",
  "description": "A beautiful, modern apartment in the heart of Berlin with great public transport connections.",
  "basePrice": 1200.00,
  "additionalCosts": 200.00,
  "rooms": 2,
  "furnished": true,
  "hasParking": true,
  "hasBalcony": true,
  "hasStorage": false,
  "size": 75.5,
  "floor": 3,
  "totalFloors": 5,
  "availableFrom": "2023-12-01",
  "energyCertificate": true,
  "yearBuilt": 2010,
  "propertyType": "APARTMENT",
  "petsAllowed": true,
  "heatingType": "GAS",
  "elevator": true,
  "barrierFree": false,
  "address": {
    "street": "Berliner Straße",
    "houseNumber": "123",
    "postalCode": "10115",
    "city": "Berlin"
  }
}'
```

### With User ID

If you want to specify a user ID, you can include it in the request:

```bash
curl -X POST http://localhost:8080/api/apartments \
  -H "Content-Type: application/json" \
  -d '{
  "userId": "00000000-0000-0000-0000-000000000001",
  "title": "Modern 2-bedroom apartment in Berlin",
  "description": "A beautiful, modern apartment in the heart of Berlin with great public transport connections.",
  "basePrice": 1200.00,
  "additionalCosts": 200.00,
  "rooms": 2,
  "furnished": true,
  "hasParking": true,
  "hasBalcony": true,
  "hasStorage": false,
  "size": 75.5,
  "floor": 3,
  "totalFloors": 5,
  "availableFrom": "2023-12-01",
  "energyCertificate": true,
  "yearBuilt": 2010,
  "propertyType": "APARTMENT",
  "petsAllowed": true,
  "heatingType": "GAS",
  "elevator": true,
  "barrierFree": false,
  "address": {
    "street": "Berliner Straße",
    "houseNumber": "123",
    "postalCode": "10115",
    "city": "Berlin"
  }
}'
```

### Expected Response

A successful request will return a 201 Created status code and the created apartment object with an assigned ID.

## Notes

- The server runs on port 8080 by default
- Make sure the service is running before executing the curl commands
- The API endpoint for creating apartments is `/api/apartments`
- Authentication is not currently required but may be implemented in the future
- The `userId` field is optional in the request - if not provided, a random UUID will be generated
- The request must include the required fields:
  - title
  - basePrice
  - additionalCosts
  - rooms

## Verifying the Service is Running

Before testing with curl, you can verify that the service is running by checking the health endpoint:

```bash
curl -X GET http://localhost:8080/actuator/health
```

If the service is running, you should receive a response like:

```json
{"status":"UP"}
```

If you get a connection refused error, the service is not running or is running on a different port.

## Swagger/OpenAPI Documentation

The service includes Swagger/OpenAPI documentation, which provides an interactive UI for testing the API. You can access it at:

```
http://localhost:8080/swagger-ui/index.html
```

This UI allows you to:
- See all available endpoints
- Test endpoints directly from the browser
- View request and response schemas
- Execute requests and see responses

This can be a more convenient way to test the API during development than using curl commands.

## Valid Enum Values

### Property Types
- APARTMENT
- STUDIO
- LOFT
- PENTHOUSE
- MAISONETTE

### Heating Types
- CENTRAL
- GAS
- ELECTRIC
- DISTRICT
- NONE

## Troubleshooting

Here are some common issues you might encounter when testing the API with curl:

1. **Connection refused**: Make sure the service is running and listening on the expected port (8080 by default).

2. **400 Bad Request**: Check that your JSON payload is valid and includes all required fields (title, basePrice, additionalCosts, rooms).

3. **415 Unsupported Media Type**: Ensure you're setting the `Content-Type: application/json` header.

4. **500 Internal Server Error**: This could indicate a problem with the service or database. Check the service logs for more details.

5. **JSON parsing errors**: Make sure your JSON is properly formatted. Common issues include:
   - Missing or extra commas
   - Unquoted property names
   - Single quotes instead of double quotes for strings
   - Trailing commas at the end of lists or objects

6. **Escaping quotes**: When using curl from a shell, you may need to escape quotes in the JSON payload. For example:
   ```bash
   curl -X POST http://localhost:8080/api/apartments \
     -H "Content-Type: application/json" \
     -d "{\"title\":\"Modern apartment\",\"basePrice\":1200.00,\"additionalCosts\":200.00,\"rooms\":2}"
   ```

7. **Using a file for the payload**: For complex JSON, it's often easier to save the payload in a file and reference it:
   ```bash
   curl -X POST http://localhost:8080/api/apartments \
     -H "Content-Type: application/json" \
     -d @payload.json
   ```
