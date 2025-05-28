package org.example.grade_predictor.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.example.grade_predictor.model.SQLiteDAO.SqliteSettingsDAO;
import org.example.grade_predictor.model.Setting;
import org.example.grade_predictor.util.FormValidator;
import org.example.grade_predictor.util.OllamaConnectionChecker;

import java.util.List;

public class SettingsController extends BaseController {

    @FXML private TextField ollamaHostField;
    @FXML private ComboBox<String> ollamaModelCombo;

    private final SqliteSettingsDAO settingsDAO = new SqliteSettingsDAO();

    public SettingsController() {
        super();
    }

    @Override
    protected void initializePageSpecificContent() {
        loadAvailableModels();
        loadCurrentSettings();
    }

    @Override
    protected void loadPageData() {
    }

    /**
     * Load available models from Ollama server using centralized checker
     */
    private void loadAvailableModels() {
        try {
            if (OllamaConnectionChecker.isOllamaConnected()) {
                List<String> availableModels = OllamaConnectionChecker.getAvailableModels();
                if (!availableModels.isEmpty()) {
                    ObservableList<String> modelList = FXCollections.observableArrayList(availableModels);
                    ollamaModelCombo.setItems(modelList);
                } else {
                    ollamaModelCombo.setItems(FXCollections.observableArrayList());
                    showAlert("Warning", OllamaConnectionChecker.getNoModelsFoundMessage());
                }
            } else {
                ollamaModelCombo.setItems(FXCollections.observableArrayList());
                showAlert("Error", OllamaConnectionChecker.getConnectionErrorMessage());
            }
        } catch (Exception e) {
            ollamaModelCombo.setItems(FXCollections.observableArrayList());
            showAlert("Error", "Error connecting to Ollama server: " + e.getMessage());
            System.err.println("Error connecting to Ollama server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Load current settings from database and populate UI fields
     */
    private void loadCurrentSettings() {
        try {
            Setting hostSetting = settingsDAO.getSettingByKey("ollama_host");
            if (hostSetting != null) {
                ollamaHostField.setText(hostSetting.getValue());
            } else {
                ollamaHostField.setText("http://localhost:11434");
            }

            Setting modelSetting = settingsDAO.getSettingByKey("ollama_model");
            if (modelSetting != null && !modelSetting.getValue().trim().isEmpty()) {
                ollamaModelCombo.setValue(modelSetting.getValue());
            }
        } catch (Exception e) {
            showAlert("Error", "Failed to load settings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Handle save settings button click
     */
    @FXML
    public void handleSaveSettings() {
        try {
            String host = ollamaHostField.getText().trim();
            String model = ollamaModelCombo.getValue();

            FormValidator.ValidationResult validationResult = FormValidator.validateSettings(host, model);
            if (!validationResult.isValid()) {
                showAlert("Validation Error", validationResult.getErrorMessage());
                return;
            }
            
            host = validationResult.getTransformedValue() != null ? validationResult.getTransformedValue() : host;

            if (OllamaConnectionChecker.isOllamaConnected()) {
                if (!OllamaConnectionChecker.isModelAvailable(model.trim())) {
                    List<String> availableModels = OllamaConnectionChecker.getAvailableModels();
                    showAlert("Model Not Found", 
                        OllamaConnectionChecker.getModelNotFoundMessage(model.trim(), availableModels));
                    
                    settingsDAO.upsertSetting("ollama_host", host);
                    settingsDAO.upsertSetting("ollama_model", "");
                    ollamaModelCombo.setValue("");
                    return;
                }
            } else {
                showAlert("Error", OllamaConnectionChecker.getConnectionErrorMessage());
                return;
            }

            settingsDAO.upsertSetting("ollama_host", host);
            settingsDAO.upsertSetting("ollama_model", model.trim());

            showAlert("Success", "Settings saved!");
        } catch (Exception e) {
            showAlert("Error", "Failed to save settings: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Refresh settings from database
     */
    public void refreshSettings() {
        loadCurrentSettings();
    }
} 