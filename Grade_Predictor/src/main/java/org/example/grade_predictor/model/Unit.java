package org.example.grade_predictor.model;

public class Unit {

    private String unit_code;
    private String unit_name;


    public Unit(String unit_code, String unit_name) {
        this.unit_code = unit_code;
        this.unit_name = unit_name;
    }

    public String getUnit_code() {
        return unit_code;
    }

    public void setUnit_code(String unit_code) {
        this.unit_code = unit_code;
    }

    public String getUnit_name() {
        return unit_name;
    }

    public void setUnit_name(String unit_name) {
        this.unit_name = unit_name;
    }




}
