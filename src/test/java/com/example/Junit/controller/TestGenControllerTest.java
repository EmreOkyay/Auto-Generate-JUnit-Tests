package com.example.Junit.controller;

import com.example.Junit.model.CodeRequest;
import com.example.Junit.model.CodeResponse;
import com.example.Junit.service.AIService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TestGenControllerTest {

    @Mock
    private AIService aiService;

    @InjectMocks
    private TestGenController testGenController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void generateTests_ShouldReturnBadRequest_WhenCodeSnippetIsEmpty() {
        CodeRequest request = new CodeRequest();
        request.setCodeSnippet("   ");

        ResponseEntity<CodeResponse> response = testGenController.generateTests(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void generateTests_ShouldReturnInternalServerError_WhenAiServiceReturnsNull() {
        CodeRequest request = new CodeRequest();
        request.setCodeSnippet("public void hello() { System.out.println(\"Hello\"); }");

        when(aiService.generateJUnitTests(anyString())).thenReturn(null);

        ResponseEntity<CodeResponse> response = testGenController.generateTests(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void generateTests_ShouldReturnOk_WhenAiServiceReturnsCode() {
        CodeRequest request = new CodeRequest();
        request.setCodeSnippet("public void hello() { System.out.println(\"Hello\"); }");

        String generatedCode = "import org.junit.jupiter.api.Test;\nclass HelloTest {}";
        when(aiService.generateJUnitTests(anyString())).thenReturn(generatedCode);

        ResponseEntity<CodeResponse> response = testGenController.generateTests(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }
}
