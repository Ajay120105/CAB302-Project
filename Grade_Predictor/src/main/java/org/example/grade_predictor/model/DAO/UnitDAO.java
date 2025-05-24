package org.example.grade_predictor.model.DAO;

import org.example.grade_predictor.model.Unit;
import org.example.grade_predictor.model.interfaces.I_Unit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UnitDAO implements I_Unit{
    public static final ArrayList<Unit> units = new ArrayList<>();


    @Override
    public void addUnit(Unit unit) {
        String code = unit.getUnit_code();
        // Check if it matches the format: 3 uppercase letters + 3 digits
        if (!code.matches("^[A-Z]{3}\\d{3}$")) {
            throw new IllegalArgumentException("Invalid unit code format. Must be 3 uppercase letters followed by 3 digits (e.g. CAB203).");
        }
        // Now insert into database
        units.add(unit);
    }

    @Override
    public void updateUnit(Unit unit) {
        for (int i = 0; i < units.size(); i++) {
            if (Objects.equals(units.get(i).getUnit_code(), unit.getUnit_code())) {
                units.set(i, unit);
                break;
            }
        }
    }

    @Override
    public void deleteUnit(Unit unit) {
        units.remove(unit);
    }

    @Override
    public Unit getUnit(String unit_code) {
        for (Unit unit : units) {
            if (Objects.equals(unit.getUnit_code(), unit_code)) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public List<Unit> getAllUnits() {
        return new ArrayList<>(units);
    }

    public UnitDAO() {
        if (units.isEmpty()) {
            // Add sample units (ensure they match the required format, e.g. 3 letters + 3 digits)
            addUnit(new Unit("CAB302", "Software Engineering"));
            addUnit(new Unit("CAB303", "Data Structures"));
            addUnit(new Unit("CAB304", "Algorithms"));
        }
    }

}
