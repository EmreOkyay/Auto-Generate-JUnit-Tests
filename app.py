from fastapi import FastAPI
from pydantic import BaseModel
from typing import List

app = FastAPI()

class CodeRequest(BaseModel):
    code: str

class CodeResponse(BaseModel):
    testCode: List[str]

@app.post("/generate-junit", response_model=CodeResponse)
def generate_junit(request: CodeRequest):
    generated_code = """public int add(int a, int b) { return a + b; }
public int multiply(int a, int b) { return a * b; }
public int divide(int a, int b) { return a / b; }"""

    lines = [line.strip() for line in generated_code.split("\n") if line.strip()]

    return CodeResponse(testCode=lines)