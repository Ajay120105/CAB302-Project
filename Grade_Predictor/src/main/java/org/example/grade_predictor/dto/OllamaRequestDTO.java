package org.example.grade_predictor.dto;

public class OllamaRequestDTO {
    private String model;

    private String prompt;

    private Boolean stream;

    private Boolean thinking;

    public OllamaRequestDTO() {};

    public OllamaRequestDTO(String prompt) {
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

    public Boolean getStream() {
        return stream;
    }

    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    public Boolean getThinking() {
        return thinking;
    }

    public void setThinking(Boolean thinking) {
        this.thinking = thinking;
    }
}
