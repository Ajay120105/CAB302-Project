package org.example.grade_predictor.dto;

public class OllamaResponse {
    private String response;

    public OllamaResponse(String response) {
        this.response = response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
