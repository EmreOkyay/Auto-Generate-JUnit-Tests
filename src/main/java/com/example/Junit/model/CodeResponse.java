package com.example.Junit.model;

import lombok.Data;

import java.util.List;

@Data
public class CodeResponse {
    private List<String> testCode;

    public CodeResponse(List<String> testCode) {
        this.testCode = testCode;
    }

    public List<String> getTestCode() {
        return testCode;
    }

    public void setTestCode(List<String> testCode) {
        this.testCode = testCode;
    }
}