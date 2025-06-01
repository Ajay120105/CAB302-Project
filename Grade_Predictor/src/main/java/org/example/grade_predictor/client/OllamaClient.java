package org.example.grade_predictor.client;

import io.github.ollama4j.OllamaAPI;
import io.github.ollama4j.exceptions.OllamaBaseException;
import io.github.ollama4j.models.response.OllamaResult;
import io.github.ollama4j.models.response.Model;
import org.example.grade_predictor.config.OllamaConfig;
import org.example.grade_predictor.dto.OllamaRequestDTO;
import org.example.grade_predictor.dto.OllamaResponseDTO;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class OllamaClient {
    private final OllamaAPI ollamaAPI;
    private final String model;

    /**
     * Constructor for the OllamaClient class
     * @param config The Ollama configuration
     */
    public OllamaClient(OllamaConfig config) {
        this.ollamaAPI = new OllamaAPI(config.getHost());
        this.model = config.getModel();
        
        this.ollamaAPI.setRequestTimeoutSeconds(30);
    }

    /**
     * Send a request to the Ollama server
     * @param request The request to send
     * @return The response from the Ollama server
     * @throws IOException If an error occurs while sending the request
     */
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
    
    /**
     * Get list of available models from Ollama server
     * @return List of model names available locally on the Ollama server
     */
    public List<String> getAvailableModels() {
        List<String> modelNames = new ArrayList<>();
        try {
            List<Model> models = ollamaAPI.listModels();
            for (Model model : models) {
                modelNames.add(model.getName());
            }
        } catch (Exception e) {
            System.err.println("Error fetching models: " + e.getMessage());
            e.printStackTrace();
        }
        return modelNames;
    }
} 