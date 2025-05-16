package org.example.grade_predictor.dto;

public class OllamaResponseDTO {
    private String response;

    public OllamaResponseDTO(String response) {
        this.response = response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getResponse() {
        return response;
    }
}
