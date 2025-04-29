import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
    url: 'http://localhost:8080',
    realm: 'immo-finder',
    clientId: 'immo-finder-front',
});

export default keycloak;