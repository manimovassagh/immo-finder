#!/bin/bash

# Login to Keycloak
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8080 --realm master --user admin --password admin

# Delete existing client
/opt/keycloak/bin/kcadm.sh delete clients/$(/opt/keycloak/bin/kcadm.sh get clients -q clientId=rent-service -r immo-finder | grep -o '"id":"[^"]*"' | cut -d'"' -f4) -r immo-finder

# Create new client
/opt/keycloak/bin/kcadm.sh create clients -r immo-finder -f /opt/keycloak/rent-service-client.json 