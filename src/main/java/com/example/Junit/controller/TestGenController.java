package com.example.Junit.controller;

import com.example.Junit.model.CodeRequest;
import com.example.Junit.model.CodeResponse;
import com.example.Junit.service.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TestGenController {

    private final AIService aiService;

    @PostMapping("/generate-tests")
    public ResponseEntity<CodeResponse> generateTests(@RequestBody CodeRequest request) {
        if (request.getCodeSnippet() == null || request.getCodeSnippet().isBlank()) {
            return ResponseEntity
                    .badRequest()
                    .body(new CodeResponse(List.of("Error: codeSnippet cannot be empty")));
        }

        String prompt = "You are a Java assistant. ONLY generate complete JUnit5 test classes for the exact Java methods provided below. " +
                "DO NOT add, modify or ignore any line. DO NOT include the original code or explanations. Return only valid Java code.\n" +
                request.getCodeSnippet();


        String testCode = aiService.generateJUnitTests(prompt);

        if (testCode == null || testCode.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new CodeResponse(List.of("Error: failed to generate test code")));
        }
        List<String> testCodeLines = Arrays.asList(testCode.split("\n"));

        return ResponseEntity.ok(new CodeResponse(testCodeLines));
    }
}