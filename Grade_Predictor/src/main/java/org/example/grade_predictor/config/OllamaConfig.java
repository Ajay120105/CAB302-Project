package org.example.grade_predictor.config;

import org.example.grade_predictor.model.SQLiteDAO.SqliteSettingsDAO;
import org.example.grade_predictor.model.Setting;

public class OllamaConfig
{
    private String host;

    private String model;
    
    private SqliteSettingsDAO settingsDAO;

    public OllamaConfig() {
        settingsDAO = new SqliteSettingsDAO();
        loadProperties();
    }

    public String getHost() {
        return this.host;
    }

    public String getModel() {
        return this.model;
    }

    /**
     * Load Ollama configuration from database
     */
    private void loadProperties() {
        try {
            Setting hostSetting = settingsDAO.getSettingByKey("ollama_host");
            this.host = (hostSetting != null) ? hostSetting.getValue() : "http://localhost:11434";
            
            Setting modelSetting = settingsDAO.getSettingByKey("ollama_model");
            this.model = (modelSetting != null) ? modelSetting.getValue() : "";
            
            // Host address fixup
            if (this.host != null && this.host.endsWith("/")) {
                this.host = this.host.substring(0, this.host.length() - 1);
            }
            
            if (this.model == null || this.model.trim().isEmpty()) {
                System.err.println("Warning: No Ollama model configured");
            }
        } catch (Exception e) {
            System.err.println("Error loading Ollama config: " + e.getMessage());
            this.host = "http://localhost:11434";
            this.model = "";
        }
    }
}
