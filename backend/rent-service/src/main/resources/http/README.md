# HTTP Request Files for Testing

This directory contains `.http` files that can be used to test the API endpoints directly from your IDE.

## What are .http files?

`.http` files are a convenient way to define and execute HTTP requests. They are supported by several IDEs and tools:

- **IntelliJ IDEA**: Built-in HTTP Client
- **Visual Studio Code**: REST Client extension
- **WebStorm**: Built-in HTTP Client
- **Postman**: Can import .http files
- **curl**: Can be converted to curl commands

## How to use

### IntelliJ IDEA / WebStorm

1. Open the `.http` file in your IDE
2. Click the "Run" icon next to each request
3. View the response in the "Response" tab

### Visual Studio Code

1. Install the "REST Client" extension
2. Open the `.http` file
3. Click "Send Request" above each request
4. View the response in a split panel

### Converting to curl

You can also convert these requests to curl commands:

1. In IntelliJ IDEA, right-click on a request and select "Copy as cURL"
2. In VS Code with REST Client, right-click and select "Copy Request as cURL"

## Available Files

- **rent-apartment-api.http**: Tests for the RentApartmentController endpoints

## Request Structure

Each request in the `.http` files follows this format:

```
### Comment describing the request
HTTP_METHOD URL
Header-Name: Header-Value

Request body (for POST, PUT, etc.)
```

Multiple requests are separated by `###`.

## Environment Variables

You can define environment variables to make the requests more flexible:

```
@host = localhost
@port = 8080

### Get health
GET http://{{host}}:{{port}}/actuator/health
```

This allows you to easily switch between different environments (local, dev, prod).

### Environment-Specific Configurations

The `.http` files include commented environment-specific configurations:

```
### Environment-specific configurations
# @name=local
# @host=localhost
# @port=8080

# @name=dev
# @host=dev-api.example.com
# @port=80

# @name=prod
# @host=api.example.com
# @port=443
# @baseUrl=https://{{host}}
```

To use a specific environment:

1. In IntelliJ IDEA / WebStorm:
   - Uncomment the environment you want to use
   - Or use the "HTTP Client Environment" dropdown in the top-right corner of the editor

2. In VS Code with REST Client:
   - Create a `.env` file in the same directory
   - Define your environments there
   - Use the "REST Client: Switch Environment" command to switch between them
