#!/bin/bash

# Login to Keycloak
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin

# Get the client ID
CLIENT_ID=$(/opt/keycloak/bin/kcadm.sh get clients -q clientId=rent-service -r immo-finder | grep -o '"id":"[^"]*"' | cut -d'"' -f4)

# Update rent-service client
/opt/keycloak/bin/kcadm.sh update clients/$CLIENT_ID -r immo-finder -s "publicClient=true" -s "standardFlowEnabled=true" -s "implicitFlowEnabled=true" -s "directAccessGrantsEnabled=true" -s "serviceAccountsEnabled=true" -s "authorizationServicesEnabled=true" 