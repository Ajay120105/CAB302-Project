package org.example.grade_predictor.model;

public class Setting {
    private int id;
    private String key;
    private String value;

    public Setting() {
    }

    /**
     * Constructor for the Setting class
     * @param key The key of the setting
     * @param value The value of the setting
     */
    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Constructor for the Setting class
     * @param id The id of the setting
     * @param key The key of the setting
     * @param value The value of the setting
     */
    public Setting(int id, String key, String value) {
        this.id = id;
        this.key = key;
        this.value = value;
    }

    /**
     * Get the id of the setting
     * @return The id of the setting
     */
    public int getId() {
        return id;
    }

    /**
     * Set the id of the setting
     * @param id The id of the setting
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get the key of the setting
     * @return The key of the setting
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the key of the setting
     * @param key The key of the setting
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the value of the setting
     * @return The value of the setting
     */
    public String getValue() {
        return value;
    }

    /**
     * Set the value of the setting
     * @param value The value of the setting
     */
    public void setValue(String value) {
        this.value = value;
    }
} 