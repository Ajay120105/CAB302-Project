package org.example.grade_predictor.model;

public class Unit {

    private String unit_code;
    private String unit_name;
    public final int CREDIT_POINTS = 2;
    private String difficulty;

    public Unit(String unit_code, String unit_name, String difficulty) {
        this.unit_code = unit_code;
        this.unit_name = unit_name;
        this.difficulty = difficulty;
    }


}
