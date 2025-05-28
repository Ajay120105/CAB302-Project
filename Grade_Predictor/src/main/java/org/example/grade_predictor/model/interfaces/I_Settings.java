package org.example.grade_predictor.model.interfaces;

import org.example.grade_predictor.model.Setting;

import java.util.List;

public interface I_Settings {
    
    /**
     * Add a new setting
     * @param setting The setting to add
     */
    void addSetting(Setting setting);
    
    /**
     * Update an existing setting
     * @param setting The setting to update
     */
    void updateSetting(Setting setting);
    
    /**
     * Delete a setting
     * @param setting The setting to delete
     */
    void deleteSetting(Setting setting);
    
    /**
     * Get a setting by its key
     * @param key The setting key
     * @return The setting if found, null otherwise
     */
    Setting getSettingByKey(String key);
    
    /**
     * Get all settings
     * @return List of all settings
     */
    List<Setting> getAllSettings();
    
    /**
     * Update or insert a setting by key
     * @param key The setting key
     * @param value The setting value
     */
    void upsertSetting(String key, String value);
} 