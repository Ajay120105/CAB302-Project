package org.example.grade_predictor.model;

public class Unit {

    private String unit_code;
    private String unit_name;

    /**
     * Constructor for the Unit class
     * @param unit_code The code of the unit
     * @param unit_name The name of the unit
     */
    public Unit(String unit_code, String unit_name) {
        this.unit_code = unit_code;
        this.unit_name = unit_name;
    }

    /**
     * Get the code of the unit
     * @return The code of the unit
     */
    public String getUnit_code() {
        return unit_code;
    }

    /**
     * Set the code of the unit
     * @param unit_code The code of the unit
     */
    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    /**
     * Get the name of the unit
     * @return The name of the unit
     */
    public String getUnit_name() {
        return unit_name;
    }

    /**
     * Set the name of the unit
     * @param unit_name The name of the unit
     */
    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }




}
