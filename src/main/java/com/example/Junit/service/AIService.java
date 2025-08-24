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
        String prompt = "You are a Java assistant. ONLY generate complete JUnit5 test classes for the exact Java methods provided below. " +
                "DO NOT add, modify or ignore any line. DO NOT include the original code or explanations. Return only valid Java code.\n" +
                codeSnippet;

        String response = webClient.post()
                .uri("/generate-junit") // FastAPI endpoint
                .bodyValue(Map.of("code", prompt))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        return response;
    }
}