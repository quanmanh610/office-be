//package com.cmcglobal.backend.config;
//
//import lombok.Getter;
//import org.keycloak.OAuth2Constants;
//import org.keycloak.admin.client.Keycloak;
//import org.keycloak.admin.client.KeycloakBuilder;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Getter
//@Configuration
//public class KeycloakAdapterConfig {
//
//    @Value("${security.oauth2.client.auth-url}")
//    private String authUrl;
//    @Value("${security.oauth2.client.realm}")
//    private String realm;
//    @Value("${security.oauth2.client.client-secret}")
//    private String secret;
//    @Value("${security.oauth2.client.client-id}")
//    private String clientId;
//
//
//
//    @Bean
//    public Keycloak keycloak() {
//        return KeycloakBuilder.builder()
//                .serverUrl(authUrl)
//                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
//                .realm(realm)
//                .clientId(clientId)
//                .clientSecret(secret)
//                .build();
//    }
//
//
//
//}
