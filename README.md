# AI-Powered JUnit Test Generator

This project is a system that automatically generates JUnit5 tests from Java methods. The backend is developed with Spring Boot, while the AI model runs through FastAPI. The goal is to reduce the time spent writing manual tests and accelerate the testing process.

## Features

- Accepts Java methods submitted by the user.
- Uses an AI model to generate JUnit5 test classes.
- Returns readable test code line by line as a JSON list.
- Provides meaningful error messages for invalid or empty inputs.


## Technologies

- **Backend:** Java 17, Spring Boot, WebClient  
- **AI Service:** Python, FastAPI, Hugging Face Transformers / LLM  
- **Data Transfer:** JSON  
- **HTTP Client:** Spring WebFlux WebClient  
- **Test Format:** JUnit5  


## Architecture

[Postman / Frontend] ---> [Spring Boot Controller] ---> [AIService (WebClient)] ---> [FastAPI AI Endpoint] ---> [LLM Model]

- The user sends Java methods via a POST request.
- The Spring Boot Controller performs input validation.
- The AIService sends a request to the FastAPI endpoint via WebClient.
- FastAPI uses the LLM model to generate test code and returns it line by line as JSON.
- Spring Boot returns the JSON list as the response.
