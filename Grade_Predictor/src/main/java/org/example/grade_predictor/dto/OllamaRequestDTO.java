package org.example.grade_predictor.dto;

public class OllamaRequestDTO {
    private String model;

    private String prompt;

    private Boolean stream;

    private Boolean thinking;

    public OllamaRequestDTO() {};

    /**
     * Constructor for the OllamaRequestDTO object
     * @param prompt The prompt to send to the Ollama server
     */
    public OllamaRequestDTO(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Get the model for the OllamaRequestDTO object
     * @return The model
     */
    public String getModel() {
        return model;
    }

    /**
     * Set the model for the OllamaRequestDTO object
     * @param model The model to set
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Get the prompt for the OllamaRequestDTO object
     * @return The prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * Set the prompt for the OllamaRequestDTO object
     * @param prompt The prompt to set
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * Get the stream option for the OllamaRequestDTO object
     * @return The stream option
     */
    public Boolean getStream() {
        return stream;
    }

    /**
     * Set the stream for the OllamaRequestDTO object
     * @param stream The stream option to set
     */
    public void setStream(Boolean stream) {
        this.stream = stream;
    }

    /**
     * Get Thinking option for the OllamaRequestDTO object
     * @return Thinking option
     */
    public Boolean getThinking() {
        return thinking;
    }

    /**
     * Set Thinking option for the OllamaRequestDTO object
     * @param thinking Thinking option to set
     */
    public void setThinking(Boolean thinking) {
        this.thinking = thinking;
    }
}
