package org.example.grade_predictor.dto;

public class OllamaResponseDTO {
    private String response;

    /**
     * Constructor for the OllamaResponseDTO object
     * @param response The response from the Ollama server
     */
    public OllamaResponseDTO(String response) {
        this.response = response;
    }

    /**
     * Set the response for the OllamaResponseDTO object
     * @param response The response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * Get the response for the OllamaResponseDTO object
     * @return The response
     */
    public String getResponse() {
        return response;
    }
}
