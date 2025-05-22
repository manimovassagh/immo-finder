# Immo Finder API - Postman Collection

This Postman collection provides a set of requests for testing the Immo Finder API.

## Contents

- **Apartments**: Endpoints for managing rental apartments
  - Create Apartment (with full details)
  - Create Apartment (Minimal - with only required fields)
  - Get All Apartments
- **Photos**: Endpoints for managing apartment photos
  - Upload Photos

## Setup Instructions

1. **Import the Collection**:
   - Open Postman
   - Click "Import" button
   - Select the `immo-finder-api.postman_collection.json` file

2. **Import the Environment**:
   - Click "Import" button again
   - Select the `immo-finder-api.postman_environment.json` file
   - Select the "Immo Finder API - Local" environment from the environment dropdown in the top right

3. **Configure the Environment**:
   - The default `baseUrl` is set to `http://localhost:8082`
   - If your API is running on a different host or port, update the `baseUrl` variable in the environment settings

## Usage

### Creating an Apartment

1. Open the "Create Apartment" or "Create Apartment (Minimal)" request
2. Click "Send" to create a new apartment
3. The response will contain the details of the created apartment, including its ID
4. The apartment ID will be automatically saved to the `apartmentId` environment variable for use in other requests

### Uploading Photos

1. Open the "Upload Photos" request
2. The `apartmentId` will be automatically filled in if you've created an apartment
3. Click "Select Files" next to the `files` parameter and choose one or more image files
4. Click "Send" to upload the photos to the apartment

### Getting All Apartments

1. Open the "Get All Apartments" request
2. Click "Send" to retrieve a list of all apartments

## Notes

- The API must be running for these requests to work
- The test scripts in the collection will automatically set the `apartmentId` variable when you create an apartment
- You can view the console logs and test results in the Postman console after sending a request