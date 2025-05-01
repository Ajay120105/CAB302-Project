package org.example.grade_predictor.dto;

public class OllamaRequest {
    private String model;

    private String prompt;

    private Boolean stream;

    public OllamaRequest() {};

    public OllamaRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }
}
