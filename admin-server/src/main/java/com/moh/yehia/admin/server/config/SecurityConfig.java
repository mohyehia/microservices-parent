package com.moh.yehia.admin.server.config;

import de.codecentric.boot.admin.server.config.AdminServerProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AdminServerProperties adminServerProperties;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        successHandler.setDefaultTargetUrl(this.adminServerProperties.getContextPath() + "/");

        httpSecurity.authorizeRequests()
                .antMatchers(this.adminServerProperties.getContextPath() + "/assets/**").permitAll()
                .antMatchers(this.adminServerProperties.getContextPath() + "/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(this.adminServerProperties.getContextPath() + "/login")
                .successHandler(successHandler)
                .and()
                .logout()
                .logoutUrl(this.adminServerProperties.getContextPath() + "/logout")
                .and()
                .httpBasic()
                .and()
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .ignoringRequestMatchers(
                        new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/instances", HttpMethod.POST.toString()),
                        new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/instances/*", HttpMethod.DELETE.toString()),
                        new AntPathRequestMatcher(this.adminServerProperties.getContextPath() + "/actuator/**")
                )
                .and()
                .rememberMe()
                .key(UUID.randomUUID().toString())
                .tokenValiditySeconds(1209600);

        return httpSecurity.build();
    }
}
