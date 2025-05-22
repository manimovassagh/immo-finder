# Rent Apartment API

This API allows you to create and manage rental apartment listings.

## Endpoints

### Create Apartment

Creates a new apartment listing.

**URL**: `/api/apartments`

**Method**: `POST`

**Auth required**: No (to be implemented)

**Request Body**:

```json
{
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
}
```

**Required Fields**:
- title
- basePrice
- additionalCosts
- rooms

**Property Types**:
- APARTMENT
- STUDIO
- LOFT
- PENTHOUSE
- MAISONETTE

**Heating Types**:
- CENTRAL
- GAS
- ELECTRIC
- DISTRICT
- NONE

**Success Response**:

- **Code**: 201 CREATED
- **Content**: The created apartment object with its ID

**Error Response**:

- **Code**: 400 BAD REQUEST
- **Content**: JSON object with field names and error messages

Example:
```json
{
  "title": "Title is required",
  "basePrice": "Base price is required"
}
```

- **Code**: 500 INTERNAL SERVER ERROR
- **Content**: JSON object with error message

Example:
```json
{
  "message": "Error message"
}
```

## Sample Request

A sample request can be found at `src/main/resources/sample-requests/create-apartment.json`.