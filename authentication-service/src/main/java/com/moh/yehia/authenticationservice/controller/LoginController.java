package com.moh.yehia.authenticationservice.controller;

import com.moh.yehia.authenticationservice.config.KeycloakProvider;
import com.moh.yehia.authenticationservice.exception.UnAuthorizedException;
import com.moh.yehia.authenticationservice.model.LoginRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Log4j2
@RequiredArgsConstructor
public class LoginController {
    private final KeycloakProvider keycloakProvider;

    @PostMapping
    public ResponseEntity<AccessTokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        log.info("loginRequest =>{}", loginRequest);
        Keycloak keycloak = keycloakProvider.keycloakWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword());
        try {
            AccessTokenResponse accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return new ResponseEntity<>(accessTokenResponse, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new UnAuthorizedException();
    }

}
