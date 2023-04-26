package com.moh.yehia.orderservice.config;

import com.moh.yehia.orderservice.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Log4j2
public class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, @NonNull HttpServletResponse httpServletResponse, @NonNull FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtFilter :: doFilterInternal :: start");
        String authHeader = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        String accessToken = authHeader.substring("Bearer ".length());
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(retrievePublicKey())
                    .build()
                    .parseClaimsJws(accessToken)
                    .getBody();
            String username = claims.get("preferred_username").toString();
            log.info("retrieved username from accessToken =>{}", username);
            String realmAccess = claims.get("realm_access").toString();
            realmAccess = realmAccess.substring("{roles=[".length(), realmAccess.length() - 2);
            log.info("roles after extracting them from realmAccess =>{}", realmAccess);
            String[] roles = realmAccess.split(",");
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + StringUtils.trimAllWhitespace(role))));
            log.info("grantedAuthorities =>{}", grantedAuthorities);
            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, grantedAuthorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            e.printStackTrace();
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        log.info("JwtFilter :: doFilterInternal :: end");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private PublicKey retrievePublicKey() throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(Constants.KEYCLOAK_PUBLIC_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(x509EncodedKeySpec);
    }

}
