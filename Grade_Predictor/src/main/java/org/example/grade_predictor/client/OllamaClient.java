package org.example.grade_predictor.client;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.OllamaRequestDTO;
import org.example.grade_predictor.dto.OllamaResponseDTO;

import java.io.IOException;

public class OllamaClient {
    private final OllamaAPI ollamaAPI;
    private final String model;

    public OllamaClient(OllamaConfig config) {
        this.ollamaAPI = new OllamaAPI(config.getHost());
        this.model = config.getModel();
        
        this.ollamaAPI.setRequestTimeoutSeconds(30);
    }

    public OllamaResponseDTO sendRequest(OllamaRequestDTO request) throws IOException {
        try {
            String modelToUse = request.getModel() != null ? request.getModel() : this.model;
            
            OllamaResult result = ollamaAPI.generate(modelToUse, request.getPrompt(), null);
            
            String response = result.getResponse();
            System.out.println("Ollama Response: " + response);
            
            return new OllamaResponseDTO(response);
            
        } catch (Exception e) {
            throw new IOException("Unexpected error: " + e.getMessage(), e);
        }
    }
    
    /**
     * Check if the Ollama service is running and accessible
     */
    public boolean isOllamaRunning() {
        try {
            return ollamaAPI.ping();
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
            return false;
        }
    }
} 