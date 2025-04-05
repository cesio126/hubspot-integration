package com.example.hubspotintegration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class HubspotAuthService {

    @Value("${hubspot.client.id}")
    private String clientId;

    @Value("${hubspot.client.secret}")
    private String clientSecret;

    @Value("${hubspot.redirect.uri}")
    private String redirectUri;

    private String accessToken;

    private final WebClient webClient = WebClient.create();

    public String generateAuthUrl() {
        return "https://app.hubspot.com/oauth/authorize?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=contacts%20oauth&response_type=code";
    }

    public void exchangeCodeForToken(String code) {
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "authorization_code");
        body.put("client_id", clientId);
        body.put("client_secret", clientSecret);
        body.put("redirect_uri", redirectUri);
        body.put("code", code);

        Map<String, Object> response = webClient.post()
                .uri("https://api.hubapi.com/oauth/v1/token")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        if (response != null) {
            accessToken = (String) response.get("access_token");
            log.info("Access Token: {}", accessToken);
        }
    }

    public String getAccessToken() {
        return accessToken;
    }
}
