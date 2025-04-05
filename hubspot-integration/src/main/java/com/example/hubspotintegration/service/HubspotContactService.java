package com.example.hubspotintegration.service;

import com.example.hubspotintegration.dto.ContactDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class HubspotContactService {

    private final HubspotAuthService authService;

    private final WebClient webClient = WebClient.create();

    public void createContact(ContactDto contactDto) {
        log.info("Criando contato: {}", contactDto);

        Map<String, Object> properties = new HashMap<>();
        properties.put("email", contactDto.getEmail());
        properties.put("firstname", contactDto.getFirstName());
        properties.put("lastname", contactDto.getLastName());

        Map<String, Object> payload = Map.of("properties", properties);

        webClient.post()
                .uri("https://api.hubapi.com/crm/v3/objects/contacts")
                .headers(headers -> headers.setBearerAuth(authService.getAccessToken()))
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .retryWhen(Retry.backoff(3, Duration.ofSeconds(2)))
                .doOnError(error -> log.error("Erro ao criar contato no HubSpot: ", error))
                .subscribe(response -> log.info("Contato criado com sucesso: {}", response));
    }

    public void handleWebhook(Map<String, Object> payload) {
        log.info("Webhook recebido: {}", payload);
    }
}
