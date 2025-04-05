package com.example.hubspotintegration.controller;

import com.example.hubspotintegration.dto.ContactDto;
import com.example.hubspotintegration.service.HubspotContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final HubspotContactService hubspotContactService;

    @PostMapping
    public ResponseEntity<String> createContact(@RequestBody ContactDto contactDto) {
        hubspotContactService.createContact(contactDto);
        return ResponseEntity.ok("Contato criado com sucesso!");
    }

    @PostMapping("/webhook")
    public ResponseEntity<Void> handleWebhook(@RequestBody Map<String, Object> payload) {
        hubspotContactService.handleWebhook(payload);
        return ResponseEntity.ok().build();
    }
}
