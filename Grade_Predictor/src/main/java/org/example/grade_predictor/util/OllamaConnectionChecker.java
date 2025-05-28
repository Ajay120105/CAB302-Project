package org.example.grade_predictor.util;

import org.example.grade_predictor.client.OllamaClient;
import org.example.grade_predictor.config.OllamaConfig;

import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for checking Ollama server connection and providing consistent error handling
 */
public class OllamaConnectionChecker {
    
    /**
     * Check if Ollama server is running and accessible
     * @return true if connected, false otherwise
     */
    public static boolean isOllamaConnected() {
        try {
            OllamaConfig config = new OllamaConfig();
            OllamaClient client = new OllamaClient(config);
            return client.isOllamaRunning();
        } catch (Exception e) {
            System.err.println("Error checking Ollama connection: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Check if Ollama server is running and has a configured model
     * @return true if connected and model is configured, false otherwise
     */
    public static boolean isOllamaReadyForGeneration() {
        try {
            OllamaConfig config = new OllamaConfig();
            if (config.getModel() == null || config.getModel().trim().isEmpty()) {
                return false;
            }
            
            OllamaClient client = new OllamaClient(config);
            return client.isOllamaRunning();
        } catch (Exception e) {
            System.err.println("Error checking Ollama readiness: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get list of available models from Ollama server
     * @return List of model names, empty list if error or no connection
     */
    public static List<String> getAvailableModels() {
        List<String> models = new ArrayList<>();
        try {
            if (!isOllamaConnected()) {
                return models;
            }
            
            OllamaConfig config = new OllamaConfig();
            OllamaClient client = new OllamaClient(config);
            models = client.getAvailableModels();
        } catch (Exception e) {
            System.err.println("Error getting available models: " + e.getMessage());
        }
        return models;
    }
    
    /**
     * Get the first available model from Ollama server
     * @return First available model name, or empty string if none available
     */
    public static String getFirstAvailableModel() {
        try {
            List<String> models = getAvailableModels();
            if (!models.isEmpty()) {
                return models.get(0);
            }
        } catch (Exception e) {
            System.err.println("Error getting first available model: " + e.getMessage());
        }
        return "";
    }
    
    /**
     * Check if a specific model exists on the Ollama server
     * @param modelName The model name to check
     * @return true if model exists, false otherwise
     */
    public static boolean isModelAvailable(String modelName) {
        if (modelName == null || modelName.trim().isEmpty()) {
            return false;
        }
        
        try {
            List<String> availableModels = getAvailableModels();
            return availableModels.contains(modelName.trim());
        } catch (Exception e) {
            System.err.println("Error checking model availability: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get appropriate error message for model not found
     * @param modelName The model that was not found
     * @param availableModels List of available models for suggestion
     * @return Error message string
     */
    public static String getModelNotFoundMessage(String modelName, List<String> availableModels) {
        StringBuilder message = new StringBuilder();
        message.append("Model '").append(modelName).append("' not found on Ollama server.");
        
        if (!availableModels.isEmpty()) {
            message.append("\nAvailable models: ").append(String.join(", ", availableModels));
        }
        
        return message.toString();
    }
    
    /**
     * Get the standard error message for Ollama connection failure
     * @return Error message string
     */
    public static String getConnectionErrorMessage() {
        return "Cannot connect to Ollama server.";
    }
    
    /**
     * Get the error message for when Ollama is connected but no model is configured
     * @return Error message string
     */
    public static String getNoModelConfiguredMessage() {
        return "No Ollama model configured. Please configure a model in Settings.";
    }
    
    /**
     * Get the error message for when no models are found on server
     * @return Error message string
     */
    public static String getNoModelsFoundMessage() {
        return "No models found on Ollama server. Please download models using 'ollama pull <model-name>' command.";
    }
    
    /**
     * Get appropriate error message based on current Ollama status
     * @return Appropriate error message
     */
    public static String getStatusErrorMessage() {
        if (!isOllamaConnected()) {
            return getConnectionErrorMessage();
        } else if (!isOllamaReadyForGeneration()) {
            return getNoModelConfiguredMessage();
        }
        return "Ollama service is ready.";
    }
    
    /**
     * Create a temporary OllamaConfig with specified host for initialization purposes
     * @param host The host address to use
     * @return Temporary OllamaConfig instance
     */
    public static OllamaConfig createTempConfig(String host) {
        return new OllamaConfig() {
            @Override
            public String getHost() {
                return host;
            }
            
            @Override
            public String getModel() {
                return "";
            }
        };
    }
} 