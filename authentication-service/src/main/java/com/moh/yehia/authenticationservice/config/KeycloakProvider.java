package com.moh.yehia.authenticationservice.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakProvider {
    @Value("${keycloak.auth-server-url}")
    public String serverUrl;

    @Value("${keycloak.realm}")
    public String realm;

    @Value("${keycloak.resource}")
    public String clientId;

    @Value("${keycloak.credentials.secret}")
    public String clientSecret;

    public Keycloak keycloakWithPasswordCredentials(String username, String password) {
        return KeycloakBuilder.builder()
                .realm(realm)
                .serverUrl(serverUrl)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }
}
