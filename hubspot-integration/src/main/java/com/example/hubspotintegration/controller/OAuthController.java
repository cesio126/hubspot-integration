package com.example.hubspotintegration.controller;

import com.example.hubspotintegration.service.HubspotAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class OAuthController {

    private final HubspotAuthService hubspotAuthService;

    @GetMapping("/url")
    public ResponseEntity<String> getAuthorizationUrl() {
        return ResponseEntity.ok(hubspotAuthService.generateAuthUrl());
    }

    @GetMapping("/callback")
    public ResponseEntity<String> processCallback(@RequestParam("code") String code) {
        hubspotAuthService.exchangeCodeForToken(code);
        return ResponseEntity.ok("Token obtido com sucesso!");
    }
}
