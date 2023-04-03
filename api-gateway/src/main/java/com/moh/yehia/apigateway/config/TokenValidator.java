package com.moh.yehia.apigateway.config;

import com.moh.yehia.apigateway.exception.UnAuthorizedException;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
@Log4j2
public class TokenValidator {

    @Value("${keycloak.publicKey}")
    private String publicKeyValue;

    public void validateAccessToken(String accessToken) {
        try {
            PublicKey publicKey = retrievePublicKey();
            log.info("public key loaded successfully!");
            Jwts.parserBuilder()
                    .setSigningKey(publicKey)
                    .build()
                    .parseClaimsJws(accessToken).getBody();
            log.info("access token is valid!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new UnAuthorizedException("Invalid or expired access token, please login again and try!");
        }
    }

    private PublicKey retrievePublicKey() throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyValue));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

}
