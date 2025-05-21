# Apartment Creation API Implementation

## Overview

This implementation provides a simple REST API for creating rental apartment listings without photos. The implementation follows a straightforward approach without a separate service layer, as requested.

## Components Created

1. **Controller**: `RentApartmentController`
   - Provides a POST endpoint at `/api/apartments`
   - Handles validation and mapping from DTO to entity

2. **DTO**: `CreateApartmentRequest`
   - Defines the structure for apartment creation requests
   - Includes validation annotations for required fields

3. **Exception Handler**: `GlobalExceptionHandler`
   - Handles validation errors and other exceptions
   - Returns appropriate error responses

4. **Sample Request**: `create-apartment.json`
   - Provides an example of a valid request

5. **Documentation**: `README.md`
   - Documents the API endpoints, request format, and responses

## How It Works

1. The client sends a POST request to `/api/apartments` with apartment data in JSON format
2. The request is validated against the constraints defined in `CreateApartmentRequest`
3. If validation fails, appropriate error messages are returned
4. If validation passes, the DTO is mapped to a `RentApartment` entity
5. The entity is saved to the database using `RentApartmentRepository`
6. The saved entity is returned with a 201 CREATED status

## Future Improvements

As mentioned in the requirements, this is a simple implementation that can be refactored later:

1. Add a service layer to separate business logic from the controller
2. Implement authentication and authorization
3. Add endpoints for retrieving, updating, and deleting apartments
4. Add support for uploading and managing photos
5. Implement pagination for listing apartments
6. Add filtering and search capabilities