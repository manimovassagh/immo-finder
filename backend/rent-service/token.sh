#!/bin/bash

curl -X POST "http://localhost:8080/realms/immo-finder/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "client_id=rent-service" \
  -d "client_secret=rent-service-secret" \
  -d "grant_type=password" \
  -d "username=admin" \
  -d "password=admin"