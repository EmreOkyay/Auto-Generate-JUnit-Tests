from fastapi import FastAPI
from pydantic import BaseModel
from transformers import pipeline

app = FastAPI()

MODEL_NAME = "Salesforce/codegen-350M-mono"

generator = pipeline(
    "text-generation",
    model=MODEL_NAME,
    tokenizer=MODEL_NAME,
    device=-1  # CPU
)

class CodeRequest(BaseModel):
    code: str

@app.post("/generate-junit")
def generate_junit(req: CodeRequest):
    prompt = f"""
You are a Java assistant.
- ONLY generate a complete JUnit5 test class in Java.
- Do NOT write Python, HTML, or any other language.
- Do NOT include the original code.
- Include @Test methods for all methods in the snippet.

Java code:
{req.code}
"""
    result = generator(
        prompt,
        max_length=1000,
        num_return_sequences=1,
        temperature=0.7,
        do_sample=True
    )

    generated_text = result[0]["generated_text"]

    if "Java code:" in generated_text:
        generated_text = generated_text.split("Java code:")[-1]

    lines = [line for line in generated_text.split("\n") if line.strip() != ""]

    return {"testCode": lines}
