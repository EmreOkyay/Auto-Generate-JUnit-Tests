package com.example.Junit.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;


@Service
public class AIService {

    private final WebClient webClient;

    public AIService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8000")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String generateJUnitTests(String codeSnippet) {
        return webClient.post()
                .uri("/generate-junit")
                .bodyValue(Map.of("code", codeSnippet))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}