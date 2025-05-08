package org.example.grade_predictor.config;

import org.example.grade_predictor.HelloApplication;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class OllamaConfig
{
    private String host;

    private String model;

    public OllamaConfig() {
        loadProperties();
    }

    public String getHost() {
        return this.host;
    }

    public String getModel() {
        return this.model;
    }

    private void loadProperties() {
        Properties props = new Properties();

        // Load properties
        try (InputStream inputStream = HelloApplication.class.getResourceAsStream("ollama.properties")) {
            if (inputStream != null) {
                props.load(inputStream);
            } else {
                System.err.println("Ollama properties file not found.");
            }
        } catch (IOException e) {
            System.err.println("Error loading ollama properties file.");
        }

        this.host = props.getProperty("ollama.host", "http://localhost:11434");
        this.model = props.getProperty("ollama.model", "deepseek-r1:14b-qwen-distill-q4_K_M");

        // Host address fixup
        if (this.host != null && this.host.endsWith("/")) {
            this.host = this.host.substring(0, this.host.length() - 1);
        }
    }
}
